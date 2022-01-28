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

import com.raleys.api.cao.odt.schedule.representation.MatrixScheduleRO;
import com.raleys.api.cao.odt.schedule.service.MatrixScheduleService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for MatrixScheduleController.class
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MatrixScheduleController.class)
public class MatrixScheduleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private MatrixScheduleService matrixScheduleService;

	/*
	 * Defining constants
	 */
	private static final String MASTER_SCHEDULE_MATRIX_URI = "/matrix/master/101";
	private static final String HOLIDAY_SCHEDULE_MATRIX_URI = "/matrix/holiday/103/22";

	List<MatrixScheduleRO> matrixList = new ArrayList<>();

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		matrixList.add(TestHelper.createMatrixRO("Sunday", "07:00", "07:00 - 09:00", "Grocery", "Natmos-Dry Grocery"));
		matrixList
				.add(TestHelper.createMatrixRO("Tuesday", "05:00", "05:00 - 079:00", "Grocery", "Natmos-Dry Grocery"));
	}

	/**
	 * Test case for retrieveMasterScheduleMatrix() for given store id without
	 * authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveMasterScheduleMatrixTestWithoutAuth() throws Exception {

		when(matrixScheduleService.retrieveMasterSchedule(Mockito.anyString(), anyInt())).thenReturn(matrixList);

		mockMvc.perform(get(MASTER_SCHEDULE_MATRIX_URI).with(csrf())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveHolidayScheduleMatrix() for given store id and
	 * holidayIdwithout authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveHolidayScheduleMatrixTestWithoutAuth() throws Exception {

		when(matrixScheduleService.retrieveHolidaySchedule(anyInt(), Mockito.anyString(), anyInt()))
				.thenReturn(matrixList);

		mockMvc.perform(get(HOLIDAY_SCHEDULE_MATRIX_URI).with(csrf())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveMasterScheduleMatrix() for given store id when matrix
	 * list is not empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveMasterScheduleMatrixTestWithAuth() throws Exception {
		when(matrixScheduleService.retrieveMasterSchedule(Mockito.anyString(), anyInt())).thenReturn(matrixList);
		mockMvc.perform(get(MASTER_SCHEDULE_MATRIX_URI).param("district", "District 1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Master Schedule Matrix fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for retrieveMasterScheduleMatrix() for given store id when matrix
	 * list is empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveMasterScheduleMatrixWhenEmptyTestWithAuth() throws Exception {
		when(matrixScheduleService.retrieveMasterSchedule(Mockito.anyString(), anyInt()))
				.thenReturn(new ArrayList<MatrixScheduleRO>());
		mockMvc.perform(get(MASTER_SCHEDULE_MATRIX_URI).param("district", "District 1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Master Schedule Matrix list is empty."));
	}

	/**
	 * Test case for retrieveMasterScheduleMatrix() for given store id when matrix
	 * list is not empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveHolidayScheduleMatrixTestWithAuth() throws Exception {
		when(matrixScheduleService.retrieveHolidaySchedule(anyInt(), Mockito.anyString(), anyInt()))
				.thenReturn(matrixList);
		mockMvc.perform(get(HOLIDAY_SCHEDULE_MATRIX_URI).param("district", "District 1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(
						jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Holiday Schedule Matrix fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	/**
	 * Test case for retrieveHolidayScheduleMatrix() for given store id when matrix
	 * list is empty with authentication
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveHolidayScheduleMatrixWhenEmptyTestWithAuth() throws Exception {
		when(matrixScheduleService.retrieveHolidaySchedule(anyInt(), Mockito.anyString(), anyInt()))
				.thenReturn(new ArrayList<MatrixScheduleRO>());
		mockMvc.perform(get(HOLIDAY_SCHEDULE_MATRIX_URI).param("district", "District 1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Holiday Schedule Matrix list is empty."));
	}
}
