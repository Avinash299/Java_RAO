package com.raleys.api.cao.odt.schedule.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.representation.SearchScheduleRO;
import com.raleys.api.cao.odt.schedule.service.SearchDeliveryScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/*
 * Test cases for SearchFilterController.class
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SearchFilterController.class)
public class SearchFilterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private SearchDeliveryScheduleService searchDeliveryScheduleService;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Defining constants
	 */
	private static final String SEARCH_SCHEDULE_LIST = "/searchSchedule/list/Bakery/1";

	List<SearchScheduleRO> searchScheduleROs = new ArrayList<>();
	AdvancedFilters advancedFilters = new AdvancedFilters();

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

		searchScheduleROs.add(TestHelper.createSearchScheduleRO("SUN", "07:00", "TUE", "06:00", "12:00", "08/07/2021",
				"09/07/2021", "district1", 12, "Natomas", "Supplies", 102));
	}

	/**
	 * Test case for retrieveCurrentSchedule() without authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveCurrentScheduleTestWithoutAuth() throws Exception {
		mockMvc.perform(post(SEARCH_SCHEDULE_LIST).with(csrf())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveCurrentSchedule() with authentication when List is not
	 * empty
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveCurrentScheduleTestWithAuthWhenListNotEmpty() throws Exception {
		when(searchDeliveryScheduleService.retrieveSchedule(anyString(), anyInt(), anyString(), anyInt(),
				any(AdvancedFilters.class))).thenReturn(searchScheduleROs);
		mockMvc.perform(post(SEARCH_SCHEDULE_LIST).param("district", "district1").param("store", "12").with(csrf())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(advancedFilters))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Search Schedule fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for retrieveCurrentSchedule() with authentication when List is
	 * empty
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveCurrentScheduleTestWithAuthWhenListEmpty() throws Exception {
		when(searchDeliveryScheduleService.retrieveSchedule(anyString(), anyInt(), anyString(), anyInt(),
				any(AdvancedFilters.class))).thenReturn(new ArrayList<SearchScheduleRO>());
		mockMvc.perform(post(SEARCH_SCHEDULE_LIST).param("district", "district1").param("store", "12").with(csrf())
				.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(advancedFilters))).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Search Schedule list is empty."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
}
