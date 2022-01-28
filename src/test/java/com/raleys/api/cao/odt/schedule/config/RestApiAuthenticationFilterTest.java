package com.raleys.api.cao.odt.schedule.config;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

import com.raleys.api.cao.odt.schedule.controller.CurrentWeekScheduleController;
import com.raleys.api.cao.odt.schedule.model.Constants;
import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.service.CurrentWeekScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for CurrentWeekScheduleController.class
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CurrentWeekScheduleController.class)
public class RestApiAuthenticationFilterTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CurrentWeekScheduleService currentWeekScheduleService;

	/*
	 * Defining constants
	 */
	private static final String CURRENT_SCHEDULE_LIST_URI = "/currentSchedule/list/1/24";

	private static final String TOKEN = "Bearer eyJraWQiOiJnbzhLTE1waUdoZjZGdWI5MXhOUXF4M2dIdlc4V01IY0h2Z3ltNWl4NGpjIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULlVmalRuYUlNdlRoX293U2FsOEdIYlBXX1c3Y21wRE9kN25HQ3EtNjdSN1kiLCJpc3MiOiJodHRwczovL2phZGVnbG9iYWwyOTE5OTIub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNjI3OTc1OTYyLCJleHAiOjE2Mjc5Nzk1NjIsImNpZCI6IjBvYTVpeXNlYnV5ZlFzTlFJNjk1IiwidWlkIjoiMDB1NWlrcnVkb3hZY3dOeXk2OTUiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImF2aW5hc2gubG9oYWthcmVAamFkZWdsb2JhbC5jb20ifQ.lbBxh8DLX7WfUb01Kqngo5vglKBO4UnPemJsjUtxdclu_2rPP7hlnc7acAuKGTh1zxRzbOwtSE2Q4nHtro-PC4ohSh4y9oG-ia7QdH6SwNBa3gDgPYwt0zMYJmHPkADpi0ESldDPAgWeUQ_Ov688W1TEbK0FneqSMthdfZ937-Xd2ejH5UKH5Z-LwFu4Nj5d4JR2PNu4IGQRIDrCehWuBe_kj6q-L2f8mAYXkMAiHmxaRJlyVVTfo0MLsuu8bFEx7JRH-jqxLvVwnOZaLIIFbGb6utglPaSs_oLbMoZywdU4GUEYUl3F3dpDei4EFbbBZd9EzO7t2n8WsZR9jMNiEQ";

	List<CurrentScheduleRO> scheduleList = new ArrayList<>();

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		scheduleList.add(TestHelper.createCurrentScheduleRO("SUN", "07:00", "TUE", "06:00", "12:00", "07/07/2021",
				"TUE", 55, 18, 103, 1, 0));
		scheduleList.add(TestHelper.createCurrentScheduleRO("TUE", "08:00", "THU", "09:00", "11:00", "08/08/2021",
				"TUE", 55, 18, 103, 2, 1));
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is not empty without bearer token
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveCurrentScheduleTestWithoutBearerToken() throws Exception {

		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(scheduleList);

		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader())).andExpect(status().isBadRequest())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()));
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is not empty with Bearer token
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveCurrentScheduleTestWithBearerToken() throws Exception {
		when(currentWeekScheduleService.retrieveCurrentSchedule(anyInt(), anyInt())).thenReturn(scheduleList);

		mockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader()).header(Constants.HEADER_STRING, TOKEN))
				.andExpect(status().isUnauthorized());
	}



}
