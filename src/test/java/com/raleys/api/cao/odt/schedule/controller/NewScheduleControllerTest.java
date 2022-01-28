package com.raleys.api.cao.odt.schedule.controller;

import static com.raleys.api.cao.odt.schedule.model.Constants.USERNAME;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.ListValidationWrapper;
import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.service.CurrentWeekScheduleService;
import com.raleys.api.cao.odt.schedule.service.NewScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for NewScheduleController.class
 */
@RunWith(SpringRunner.class)
@WebMvcTest(NewScheduleController.class)
public class NewScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Mock
	private BindingResult bindingResult;

	List<ObjectError> errorList;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private NewScheduleService newScheduleService;

	@MockBean
	private CurrentWeekScheduleService currentScheduleService;

	/*
	 * Defining constants
	 */
	private static final String SAVE_NEW_SCHEDULE_URI = "/newSchedule/save/102/24";

	ListValidationWrapper<DeliverySchedule> newSchedulewrapper = new ListValidationWrapper<>();
	List<DeliveryScheduleDTO> newScheduleDTOList = new ArrayList<>();
	List<CurrentScheduleRO> currentScheduleROList = new ArrayList<>();

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

		newSchedulewrapper.getList().add(TestHelper.createNewSchedule(102, "SUN", "07:00", "TUE", "06:00", "12:00",
				"07/07/2021", "TUE", 55, 18, 102, 0, "Apply"));
		newSchedulewrapper.getList().add(TestHelper.createNewSchedule(102, "TUE", "08:00", "THU", "09:00", "11:00",
				"08/08/2021", "TUE", 56, 19, 102, 1, "Apply"));

		newSchedulewrapper.getList().forEach(ns -> {
			newScheduleDTOList.add(new DeliveryScheduleDTO(ns.getOrderDay(), ns.getDsId()));
		});
	}

	/**
	 * Test case for saveNewSchedule() for given store id and vendor name without
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void saveNewScheduleWithoutAuth() throws Exception {
		when(newScheduleService.saveAllNewSchedule(anyList(), anyInt(), anyInt(), anyString()))
				.thenReturn(newScheduleDTOList);
		mockMvc.perform(post(SAVE_NEW_SCHEDULE_URI).with(csrf().asHeader()).accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchedulewrapper)))
				.andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for saveNewSchedule() for given store id and vendor name when store
	 * id, new schedules are valid and current schedule list is not empty with
	 * authentication, accepts list of new schedules in body. new schedules are
	 * valid when contains no duplicate order day returns ok when new schedules and
	 * current schudules contains different order day
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void saveNewScheduleTestWithAuthWhenNewSchedulesValidAndCurrentScheduleNotEmptyForOK() throws Exception {
		newSchedulewrapper.getList().add(TestHelper.createNewSchedule(null, "WED", "07:00", "TUE", "06:00", "12:00",
				"07/07/2021", "TUE", 55, 18, 102, 1, "Apply"));

		currentScheduleROList.add(TestHelper.createCurrentScheduleRO("THU", "07:00", "TUE", "06:00", "12:00",
				"07/07/2021", "TUE", 55, 18, 102, 102, 0));
		currentScheduleROList.add(TestHelper.createCurrentScheduleRO("FRI", "08:00", "THU", "09:00", "11:00",
				"08/08/2021", "TUE", 56, 19, 102, 102, 1));

		when(currentScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(currentScheduleROList);
		TestHelper.setNewScheduleDTO();
		mockMvc.perform(
				post(SAVE_NEW_SCHEDULE_URI).header(USERNAME, "admin").with(csrf().asHeader()).accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchedulewrapper)))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("New Schedules saved/updated successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	@Test
	@WithMockUser
	public void retrieveFutureScheduleWithAuthForInvalidHolidayId() throws Exception {
		newSchedulewrapper.getList().add(TestHelper.createNewSchedule(102, "", "08:00", "THU", "09:00", "11:00",
				"08/08/2021", "TUE", 56, 19, 102, 1, "Apply"));
		when(currentScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(currentScheduleROList);

		mockMvc.perform(post(SAVE_NEW_SCHEDULE_URI).with(csrf().asHeader()).accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(newSchedulewrapper)))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

}
