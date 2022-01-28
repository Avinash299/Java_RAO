package com.raleys.api.cao.odt.schedule.utils;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import com.raleys.api.cao.odt.schedule.exception.InvalidHolidayException;
import com.raleys.api.cao.odt.schedule.exception.InvalidRequestException;
import com.raleys.api.cao.odt.schedule.exception.InvalidStoreException;
import com.raleys.api.cao.odt.schedule.exception.InvalidVendorException;
import com.raleys.api.cao.odt.schedule.util.CustomValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomValidator.class)
public class CustomValidatorTest {

	private CustomValidator validator;

	@MockBean
	private BindingResult bindingResult;

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		validator = new CustomValidator();

	}

	@After
	public void tearDown() {
		validator = new CustomValidator();

	}

	/**
	 * Test case for validating vendor id
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void validateVendor() throws Exception {

		CustomValidator validator = new CustomValidator();

		assertThrows(InvalidVendorException.class, () -> validator.validateStoreandVendor(103, 0));

	}

	/**
	 * Test case for validating holiday id
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void validateHolidayId() throws Exception {

		assertThrows(InvalidHolidayException.class, () -> validator.validateHoliday(0));

	}

	/**
	 * Test case for validating store id
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void validateStore() throws Exception {

		TestHelper.setErrorMessage(HttpStatus.NOT_FOUND.value(), "0 is not a valid store");

		assertThrows(InvalidStoreException.class, () -> validator.validateStoreandVendor(0, 1));

	}

	/**
	 * Test case for validating new schedule request
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void validateNewScheduleRequest() throws Exception {

		when(bindingResult.hasErrors()).thenReturn(true);
		assertThrows(InvalidRequestException.class, () -> validator.validateNewScheduleRequest(bindingResult));

	}

}
