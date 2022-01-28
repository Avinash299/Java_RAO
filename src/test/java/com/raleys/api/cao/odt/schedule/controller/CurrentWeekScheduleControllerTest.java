package com.raleys.api.cao.odt.schedule.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.service.CurrentWeekScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for CurrentWeekScheduleController.class
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CurrentWeekScheduleController.class)
public class CurrentWeekScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	@MockBean
	private CurrentWeekScheduleService currentWeekScheduleService;

	/*
	 * Defining constants
	 */
	private static final String CURRENT_SCHEDULE_LIST_URI = "/currentSchedule/list/1/24";
	private static final String CURRENT_SCHEDULE_LIST_STID0_URI = "/currentSchedule/list/0/24";

	List<CurrentScheduleRO> scheduleList = new ArrayList<>();

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		
		scheduleList.add(TestHelper.createCurrentScheduleRO("SUN", "07:00", "TUE", "06:00", "12:00", "07/07/2021",
				"TUE", 55, 18, 103, 1,0));
		scheduleList.add(TestHelper.createCurrentScheduleRO("TUE", "08:00", "THU", "09:00", "11:00", "08/08/2021",
				"TUE", 55, 18, 103, 2,1));
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * without authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveCurrentScheduleTestWithoutAuth() throws Exception {

		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(scheduleList);

		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is not empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveCurrentScheduleTestWithAuth() throws Exception {

		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(scheduleList);

		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("CurrentSchedule fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveCurrentScheduleTestWhenEmptyWithAuth() throws Exception {

		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt()))
				.thenReturn(new ArrayList<CurrentScheduleRO>());

		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("CurrentSchedule list is empty."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	
	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveCurrentScheduleTestWhenEmpty() throws Exception {

		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt()))
				.thenReturn(new ArrayList<CurrentScheduleRO>());
		
		TestHelper.setResponse(HttpStatus.OK.value(), "CurrentSchedule list is empty.",new ArrayList<CurrentScheduleRO>());

		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isOk())
				
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	

	/**
	 * method to retrieve current schedule without authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveCurrentScheduleTestWithoutOktaAuth() throws Exception {
		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(scheduleList);
		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_STID0_URI)).andExpect(status().isUnauthorized());
	}

	
}
