package com.raleys.api.cao.odt.schedule.controller;

import static com.raleys.api.cao.odt.schedule.model.Constants.USERNAME;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import com.raleys.api.cao.odt.schedule.dto.CloneScheduleDTO;
import com.raleys.api.cao.odt.schedule.exception.DateParseException;
import com.raleys.api.cao.odt.schedule.model.Constants.NAMEDSCHEDULEACTION;
import com.raleys.api.cao.odt.schedule.model.Maintenance;
import com.raleys.api.cao.odt.schedule.model.ResponseMessage;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;
import com.raleys.api.cao.odt.schedule.representation.NamedScheduleRO;
import com.raleys.api.cao.odt.schedule.service.NamedScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for NamedScheduleController class
 */
@RunWith(SpringRunner.class)
@WebMvcTest(NamedScheduleController.class)
public class NamedScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private DateFormat simpleDateFormat;

	@MockBean
	private NamedScheduleService namedScheduleService;

	CloneScheduleDTO namedSchedule;
	Maintenance maintenance;
	List<NamedScheduleRO> holidays = new ArrayList<>();
	List<FutureScheduleRO> futureScheduleROs = new ArrayList<>();

	/*
	 * Defining constants
	 */
	private static final String NAMED_SCHEDULE_LIST_URI = "/namedSchedule/list";
	private static final String NAMED_SCHEDULE_CLONE_URI = "/namedSchedule/clone";
	private static final String NAMED_SCHEDULE_DELETE_URI = "/namedSchedule/delete/1";
	private static final String NAMED_SCHEDULE_UPDATE_URI = "/namedSchedule/update";
	private static final String NAMED_SCHEDULE_PUBLISH_URI = "/namedSchedule/publish";
	private static final String NAMED_SCHEDULE_ACTIVATE_URI = "/namedSchedule/active";

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

		holidays.add(TestHelper.createHolidayRO(1, "Thanks Giving -2020", "23/09/2020", "24/08/2020", "Publish"));
		holidays.add(TestHelper.createHolidayRO(2, "Christmas- 2020", "24/12/2020", "26/12/2020", "Publish"));
		holidays.add(TestHelper.createHolidayRO(3, "Thanks Giving- 2021", "23/09/2021", "24/09/2021", "Publish"));
		holidays.add(TestHelper.createHolidayRO(4, "Christmas - 2021", "24/12/2021", "25/12/2021", "Publish"));

		this.namedSchedule = TestHelper.cloneNamedSchedule("ThanksGiving2021", "Create", 1);
		this.maintenance = TestHelper.createMaintenance("Christmas - 2021", "23/08/2021", "30/08/2021");
	}

	/**
	 * Test case for retrieveHolidays() without authentications
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveHolidaysTestWithoutAuth() throws Exception {
		when(namedScheduleService.retrieveNamedSchedule(false)).thenReturn(holidays);

		mockMvc.perform(get(NAMED_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveHolidays() when holiday list is not empty with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveHolidaysTestWithAuth() throws Exception {
		when(namedScheduleService.retrieveNamedSchedule(false)).thenReturn(holidays);

		mockMvc.perform(get(NAMED_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_LIST))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for retrieveHolidays() when holiday list is empty with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveHolidaysTestWhenEmptyWithAuth() throws Exception {
		when(namedScheduleService.retrieveNamedSchedule(false)).thenReturn(new ArrayList<NamedScheduleRO>());

		mockMvc.perform(get(NAMED_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_EMPTY))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for cloneNamedSchedule() when namedSchedule is not null with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void cloneNamedScheduleTestWhenNotNullWithAuth() throws Exception {
		when(namedScheduleService.cloneNamedSchedule(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(namedSchedule);
		mockMvc.perform(post(NAMED_SCHEDULE_CLONE_URI).param("name", "ThanksGiving2021").header(USERNAME, "admin")
				.with(csrf().asHeader()).accept(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_CLONE))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for cloneNamedSchedule() when namedSchedule is null with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void cloneNamedScheduleTestWhentNullWithAuth() throws Exception {
		when(namedScheduleService.cloneNamedSchedule(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		mockMvc.perform(post(NAMED_SCHEDULE_CLONE_URI).param("name", "ThanksGiving2021").header(USERNAME, "admin")
				.with(csrf().asHeader()).accept(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_EXIST))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for deleteNamedSchedule() when namedSchedule status is Create with
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void deleteNamedScheduleTestWhenStatusCreateWithAuth() throws Exception {
		when(namedScheduleService.deleteNamedSchedule(anyInt())).thenReturn(1);
		mockMvc.perform(delete(NAMED_SCHEDULE_DELETE_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_DELETE))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for deleteNamedSchedule() when namedSchedule status is not Create
	 * with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void deleteNamedScheduleTestWhenStatusNotCreateWithAuth() throws Exception {
		when(namedScheduleService.deleteNamedSchedule(anyInt())).thenReturn(0);
		mockMvc.perform(delete(NAMED_SCHEDULE_DELETE_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.BAD_REQUEST))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	@Test
	@WithMockUser
	public void updateNamedScheduleTestWhenUpdatedWithAuth() throws Exception {
		when(namedScheduleService.updateNamedSchedule(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Maintenance.class))).thenReturn(NAMEDSCHEDULEACTION.UPDATE.toString());

		mockMvc.perform(
				put(NAMED_SCHEDULE_UPDATE_URI).header(USERNAME, "admin").with(csrf().asHeader()).accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(maintenance)))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_UPDATE))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	@Test
	@WithMockUser
	public void updateNamedScheduleTestWhenNotUpdatededWithAuth() throws Exception {
		when(namedScheduleService.updateNamedSchedule(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Maintenance.class))).thenReturn(NAMEDSCHEDULEACTION.PUBLISH.toString());

		mockMvc.perform(
				put(NAMED_SCHEDULE_UPDATE_URI).header(USERNAME, "admin").with(csrf().asHeader()).accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(maintenance)))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(NAMEDSCHEDULEACTION.PUBLISH.toString()))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	@Test
	@WithMockUser
	public void publishNamedScheduleTestWhenPublishedWithAuth() throws Exception {
		when(namedScheduleService.updateNamedSchedule(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Maintenance.class))).thenReturn(NAMEDSCHEDULEACTION.PUBLISH.toString());

		mockMvc.perform(put(NAMED_SCHEDULE_PUBLISH_URI).header(USERNAME, "admin").with(csrf().asHeader())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(maintenance))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_PUBLISHED))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	@Test
	@WithMockUser
	public void publishNamedScheduleTestWhenNotPublishedWithAuth() throws Exception {
		when(namedScheduleService.updateNamedSchedule(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Maintenance.class))).thenReturn(NAMEDSCHEDULEACTION.UPDATE.toString());

		mockMvc.perform(put(NAMED_SCHEDULE_PUBLISH_URI).header(USERNAME, "admin").with(csrf().asHeader())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(maintenance))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(NAMEDSCHEDULEACTION.UPDATE.toString()))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for deleteNamedSchedule() when namedSchedule status is not Create
	 * with authentication when exception
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void deleteNamedScheduleTestWhenStatusNotCreateWithAuthException() throws Exception {
		when(namedScheduleService.deleteNamedSchedule(anyInt())).thenThrow(new DateParseException("", ""));
		mockMvc.perform(delete(NAMED_SCHEDULE_DELETE_URI).with(csrf().asHeader()))
				.andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for validating named schedule when activated
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void publishNamedScheduleTestWhenActivatedWithAuth() throws Exception {
		when(namedScheduleService.updateNamedSchedule(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Maintenance.class))).thenReturn(NAMEDSCHEDULEACTION.ACTIVE.toString());

		mockMvc.perform(put(NAMED_SCHEDULE_ACTIVATE_URI).header(USERNAME, "admin").with(csrf().asHeader())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(maintenance))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(ResponseMessage.NAMEDSCHEDULE_ACTIVATED))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	@Test
	@WithMockUser
	public void publishNamedScheduleTestWhenNotActivatedWithAuth() throws Exception {
		when(namedScheduleService.updateNamedSchedule(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Maintenance.class))).thenReturn(NAMEDSCHEDULEACTION.UPDATE.toString());

		mockMvc.perform(put(NAMED_SCHEDULE_ACTIVATE_URI).header(USERNAME, "admin").with(csrf().asHeader())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(maintenance))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.BAD_REQUEST.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value(NAMEDSCHEDULEACTION.UPDATE.toString()))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

}
