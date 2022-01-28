package com.raleys.api.cao.odt.schedule.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.MatrixDAO;
import com.raleys.api.cao.odt.schedule.model.Matrix;
import com.raleys.api.cao.odt.schedule.representation.MatrixScheduleRO;
import com.raleys.api.cao.odt.schedule.service.impl.MatrixScheduleServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for CurrentWeekScheduleServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class MatrixScheduleServiceTest {

	@Mock
	private MatrixDAO matrixDAO;

	@InjectMocks
	private MatrixScheduleServiceImpl matrixScheduleService;

	List<Matrix> matrixs = new ArrayList<>();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		matrixs.add(TestHelper.createMatrix(1, "MON", "01:00", "01:00", "03:00", "SSI Bakery", "Bakery"));
		matrixs.add(TestHelper.createMatrix(2, "TUE", "03:00", "05:00", "07:00", "SSI FSD", "Grocery"));
		matrixs.add(TestHelper.createMatrix(3, "THU", "05:00", "05:00", "09:00", "SSI FSD", "Grocery"));
	}

	/**
	 * Test case for retrieveMasterScheduleMatrixByDistrictAndStore() for given
	 * store id and district when schedule list is not empty
	 */
	@Test
	public void retrieveMasterScheduleMatrixByDistrictAndStoreTest() {
		when(matrixDAO.retrieveMasterScheduleMatrixByDistrictAndStore(Mockito.anyString(), anyInt()))
				.thenReturn(matrixs);
		List<MatrixScheduleRO> list = matrixScheduleService.retrieveMasterSchedule(Mockito.anyString(), anyInt());
		assertEquals(3, list.size());
		verify(matrixDAO).retrieveMasterScheduleMatrixByDistrictAndStore(Mockito.anyString(), anyInt());
	}

	/**
	 * Test case for retrieveMasterScheduleMatrixByStore() for given store id and
	 * district is null when schedule list is not empty
	 */
	@Test
	public void retrieveMasterScheduleMatrixByStoreTest() {
		when(matrixDAO.retrieveMasterScheduleMatrixByStore(anyInt())).thenReturn(matrixs);
		List<MatrixScheduleRO> list = matrixScheduleService.retrieveMasterSchedule(null, anyInt());
		assertEquals(3, list.size());
		verify(matrixDAO).retrieveMasterScheduleMatrixByStore(anyInt());
	}

	/**
	 * Test case for retrieveHolidayScheduleMatrixByDistrictAndStore() for given
	 * store id and district when schedule list is not empty
	 */
	@Test
	public void retrieveHolidayScheduleMatrixByDistrictAndStoreTest() {
		when(matrixDAO.retrieveHolidayScheduleMatrixByDistrictAndStore(anyInt(), Mockito.anyString(), anyInt()))
				.thenReturn(matrixs);
		List<MatrixScheduleRO> list = matrixScheduleService.retrieveHolidaySchedule(anyInt(), Mockito.anyString(),
				anyInt());
		assertEquals(3, list.size());
		verify(matrixDAO).retrieveHolidayScheduleMatrixByDistrictAndStore(anyInt(), Mockito.anyString(), anyInt());
	}

	/**
	 * Test case for retrieveHolidayScheduleMatrixByStore() for given store id and
	 * district is null when schedule list is not empty
	 */
	@Test
	public void retrieveHolidayScheduleMatrixByStoreTest() {
		when(matrixDAO.retrieveHolidayScheduleMatrixByStore(anyInt(), anyInt())).thenReturn(matrixs);
		List<MatrixScheduleRO> list = matrixScheduleService.retrieveHolidaySchedule(anyInt(), null, anyInt());
		assertEquals(3, list.size());
		verify(matrixDAO).retrieveHolidayScheduleMatrixByStore(anyInt(), anyInt());
	}
}
