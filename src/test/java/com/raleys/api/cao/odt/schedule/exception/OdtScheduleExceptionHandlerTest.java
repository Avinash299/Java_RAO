package com.raleys.api.cao.odt.schedule.exception;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.raleys.api.cao.odt.schedule.controller.CurrentWeekScheduleController;
import com.raleys.api.cao.odt.schedule.controller.StoreVendorInfoController;

/**
 * Test class for OdtScheduleExceptionHandler
 *
 */
@RunWith(SpringRunner.class)
public class OdtScheduleExceptionHandlerTest {

	private MockMvc currentWeekScheduleMockMvc;
	private MockMvc storeVendorInfoMockMvc;

	@Mock
	private CurrentWeekScheduleController currentWeekScheduleController;

	@Mock
	private StoreVendorInfoController storeVendorInfoController;

	/*
	 * Defining constants
	 */
	private static final String CURRENT_SCHEDULE_LIST_URI = "/currentSchedule/list/1/24";
	private static final String VENDORS_URI = "/vendors";
	private static final String JSON_PATH_STATUS = "$.statusCode";
	private static final String JSON_PATH_MESSAGE = "$.message";

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.currentWeekScheduleMockMvc = MockMvcBuilders.standaloneSetup(currentWeekScheduleController)
				.setControllerAdvice(new OdtScheduleExceptionHandler()).build();

		this.storeVendorInfoMockMvc = MockMvcBuilders.standaloneSetup(storeVendorInfoController)
				.setControllerAdvice(new OdtScheduleExceptionHandler()).build();
	}

	/**
	 * Test case for resourceNotFoundException() of @RestControllerAdvice
	 * OdtScheduleExceptionHandler.class for CurrentWeekScheduleController
	 * 
	 * @throws ResourceNotFoundException
	 *             with status code 404
	 */
	@Test
	public void resourceNotFoundExceptionTestForCurrentWeekScheduleController() throws Exception {

		when(currentWeekScheduleController.retrieveCurrentSchedule(anyInt(), anyInt()))
				.thenThrow(new ResourceNotFoundException("Not Found"));

		currentWeekScheduleMockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader()))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath(JSON_PATH_STATUS).value(HttpStatus.NOT_FOUND.value()))
				.andExpect(jsonPath(JSON_PATH_MESSAGE).value("Not Found"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	/**
	 * Test case for globalExceptionHandler() of @RestControllerAdvice
	 * OdtScheduleExceptionHandler.class for CurrentWeekScheduleController
	 * 
	 * @throws Exception
	 *             with status code 500
	 */
	@Test
	public void globalExceptionHandlerTestForCurrentWeekScheduleController() throws Exception {

		when(currentWeekScheduleController.retrieveCurrentSchedule(anyInt(), anyInt())).thenAnswer(invocation -> {
			throw new Exception("Internal Server Error");
		});

		currentWeekScheduleMockMvc.perform(get(CURRENT_SCHEDULE_LIST_URI).with(csrf().asHeader()))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath(JSON_PATH_STATUS).value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.andExpect(jsonPath(JSON_PATH_MESSAGE).value("Internal Server Error"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	/**
	 * Test case for resourceNotFoundException() of @RestControllerAdvice
	 * OdtScheduleExceptionHandler.class for StoreVendorInfoController
	 * 
	 * @throws ResourceNotFoundException
	 *             with status code 404
	 */
	@Test
	public void resourceNotFoundExceptionTestForStoreVendorInfoController() throws Exception {

		when(storeVendorInfoController.retrieveVendors(null)).thenThrow(new ResourceNotFoundException("Not Found"));

		storeVendorInfoMockMvc.perform(get(VENDORS_URI).with(csrf().asHeader())).andExpect(status().isNotFound())
				.andExpect(jsonPath(JSON_PATH_STATUS).value(HttpStatus.NOT_FOUND.value()))
				.andExpect(jsonPath(JSON_PATH_MESSAGE).value("Not Found"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	/**
	 * Test case for globalExceptionHandler() of @RestControllerAdvice
	 * OdtScheduleExceptionHandler.class for StoreVendorInfoController
	 * 
	 * @throws Exception
	 *             with status code 500
	 */
	@Test
	public void globalExceptionHandlerTestForStoreVendorInfoController() throws Exception {

		when(storeVendorInfoController.retrieveVendors(null)).thenAnswer(invocation -> {
			throw new Exception("Internal Server Error");
		});

		storeVendorInfoMockMvc.perform(get(VENDORS_URI).with(csrf().asHeader()))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath(JSON_PATH_STATUS).value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.andExpect(jsonPath(JSON_PATH_MESSAGE).value("Internal Server Error"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
}
