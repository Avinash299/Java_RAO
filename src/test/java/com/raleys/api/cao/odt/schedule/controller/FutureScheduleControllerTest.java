package com.raleys.api.cao.odt.schedule.controller;

import static com.raleys.api.cao.odt.schedule.model.Constants.USERNAME;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.exception.FutureScheduleNotUpdatedException;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;
import com.raleys.api.cao.odt.schedule.service.FutureScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for FutureScheduleController.class
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FutureScheduleController.class)
public class FutureScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private FutureScheduleService futureScheduleService;

	/* List<NamedSchedule> holiday = new ArrayList<>(); */
	List<FutureScheduleRO> futureScheduleROs = new ArrayList<>();

	DeliverySchedule deliverySchedule;
	DeliveryScheduleDTO deliveryScheduleDTO;
	Response response;

	/*
	 * Defining constants
	 */
	private static final String FUTURE_SCHEDULE_RETRIEVE_URI = "/futureSchedule/list/102/18/1";
	private static final String FUTURE_SCHEDULE_SAVE_URI = "/futureSchedule/save/102/18/1";
	private static final String FUTURE_SCHEDULE_REMOVE_URI = "/futureSchedule/remove/1";
	private static final String FUTURE_SCHEDULE_RETRIEVE_URI_INVALID_STORE = "/futureSchedule/list/0/1/1";
	private static final String FUTURE_SCHEDULE_RETRIEVE_URI_INVALID_VENDOR = "/futureSchedule/list/1/0/1";
	private static final String FUTURE_SCHEDULE_RETRIEVE_URI_INVALID_HOLIDAY = "/futureSchedule/list/1/1/0";

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

		futureScheduleROs.add(TestHelper.createFutureScheduleRO("SUN", "07:00", "TUE", "06:00", "12:00", "08/07/2021",
				"TUE", 1200, 1300, 1400, 102, 1));
		futureScheduleROs.add(TestHelper.createFutureScheduleRO("MON", "08:00", "WED", "07:00", "01:00", "10/07/2021",
				"WED", 1400, 1500, 1600, 102, 1));
		futureScheduleROs.add(TestHelper.createFutureScheduleRO("TUE", "09:00", "THU", "08:00", "02:00", "12/07/2021",
				"THU", 1600, 1700, 1800, 102, 1));

		this.deliverySchedule = TestHelper.createNewSchedule(102, "SUN", "07:00", "TUE", "06:00", "12:00", "07/07/2021",
				"TUE", 55, 18, 102, 1, "create");
		this.response = new Response(HttpStatus.NO_CONTENT.value(), "Future Schedule logically removed  Sucessfully",
				1);
		this.deliveryScheduleDTO = new DeliveryScheduleDTO("Sunday", 1);
	}

	/**
	 * Test case for retrieveFutureSchedule() without authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveFutureScheduleWithoutAuth() throws Exception {
		when(futureScheduleService.retrieveFutureSchedule(anyInt(), anyInt(), anyInt())).thenReturn(futureScheduleROs);

		mockMvc.perform(get(FUTURE_SCHEDULE_RETRIEVE_URI).with(csrf().asHeader())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveFutureSchedule() when holiday list is empty with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveFutureScheduleWithAuthWhenFutureScheduleEmpty() throws Exception {
		when(futureScheduleService.retrieveFutureSchedule(anyInt(), anyInt(), anyInt()))
				.thenReturn(new ArrayList<FutureScheduleRO>());

		mockMvc.perform(get(FUTURE_SCHEDULE_RETRIEVE_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("FutureSchedule list is empty."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for retrieveFutureSchedule() when holiday list is not empty with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveFutureScheduleWithAuthWhenFutureScheduleNotEmpty() throws Exception {
		when(futureScheduleService.retrieveFutureSchedule(anyInt(), anyInt(), anyInt())).thenReturn(futureScheduleROs);

		mockMvc.perform(get(FUTURE_SCHEDULE_RETRIEVE_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("FutureSchedule fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for saveFutureSchedule() with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void saveFutureScheduleTestWithAuth() throws Exception {
		when(futureScheduleService.saveFutureSchedule(Mockito.any(DeliverySchedule.class), anyInt(), anyInt(), anyInt(),
				Mockito.anyString())).thenReturn(deliveryScheduleDTO);
		mockMvc.perform(post(FUTURE_SCHEDULE_SAVE_URI).header(USERNAME, "admin").with(csrf().asHeader())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(deliverySchedule))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Future Schedule saved/updated successfully."));
	}

	/**
	 * Test case for saveFutureSchedule() with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void saveFutureScheduleBadRequestTestWithAuth() throws Exception {
		when(futureScheduleService.saveFutureSchedule(Mockito.any(DeliverySchedule.class), anyInt(), anyInt(), anyInt(),
				Mockito.anyString())).thenReturn(null);
		mockMvc.perform(
				post(FUTURE_SCHEDULE_SAVE_URI).param("user", "admin").with(csrf().asHeader()).accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(deliverySchedule)))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE)
						.value("Holiday Schedule already active, Please check the maintenance screen."));
	}

	/**
	 * Test case for future schedule with invalid store
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveFutureScheduleWithAuthForInvalidStore() throws Exception {
		when(futureScheduleService.retrieveFutureSchedule(anyInt(), anyInt(), anyInt()))
				.thenReturn(new ArrayList<FutureScheduleRO>());

		mockMvc.perform(get(FUTURE_SCHEDULE_RETRIEVE_URI_INVALID_STORE).with(csrf().asHeader()))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for future schedule with invalid vendor id
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveFutureScheduleWithAuthForInvalidVendor() throws Exception {
		when(futureScheduleService.retrieveFutureSchedule(anyInt(), anyInt(), anyInt()))
				.thenReturn(new ArrayList<FutureScheduleRO>());

		mockMvc.perform(get(FUTURE_SCHEDULE_RETRIEVE_URI_INVALID_VENDOR).with(csrf().asHeader()))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for future schedule with invalid holiday id
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveFutureScheduleWithAuthForInvalidHolidayId() throws Exception {
		when(futureScheduleService.retrieveFutureSchedule(anyInt(), anyInt(), anyInt()))
				.thenReturn(new ArrayList<FutureScheduleRO>());

		mockMvc.perform(get(FUTURE_SCHEDULE_RETRIEVE_URI_INVALID_HOLIDAY).with(csrf().asHeader()))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for removeFutureSchedule() with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void removeFutureScheduleTest() throws Exception {
		when(futureScheduleService.removeScheduleDay(Mockito.anyString(), Mockito.anyInt())).thenReturn(1);

		mockMvc.perform(put(FUTURE_SCHEDULE_REMOVE_URI).header(USERNAME, "admin").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.NO_CONTENT.value())).andExpect(
						jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Future Schedule logically removed  Sucessfully"));
	}

	/**
	 * Test case for removeScheduleDay() when variable update=0
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void removeScheduleDayTestWhenNotUpdated() throws Exception {
		when(futureScheduleService.removeScheduleDay(Mockito.anyString(), Mockito.anyInt())).thenReturn(0);

		mockMvc.perform(put(FUTURE_SCHEDULE_REMOVE_URI).param("user", "admin").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Future Schedule is not updated "));
	}

	/**
	 * Test case for removeScheduleDay() when variable update=0 when exception
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void removeScheduleDayTestWhenException() throws Exception {
		when(futureScheduleService.removeScheduleDay(Mockito.anyString(), Mockito.anyInt()))
				.thenThrow(FutureScheduleNotUpdatedException.class);
		TestHelper.setErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Future Schedule is not updated");

		mockMvc.perform(put(FUTURE_SCHEDULE_REMOVE_URI).header(USERNAME, "admin").with(csrf().asHeader()))
				.andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));

	}

}
