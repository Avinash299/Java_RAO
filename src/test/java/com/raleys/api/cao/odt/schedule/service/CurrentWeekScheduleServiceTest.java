package com.raleys.api.cao.odt.schedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.DeliveryScheduleDAO;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.service.impl.CurrentWeekScheduleServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for CurrentWeekScheduleServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class CurrentWeekScheduleServiceTest {

	@Mock
	private DeliveryScheduleDAO deliveryScheduleDAO;

	@InjectMocks
	private CurrentWeekScheduleServiceImpl currentWeekScheduleService;

	List<WeeklySchedule> weeklySchedule = new ArrayList<>();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		weeklySchedule.add(TestHelper.createWeeklySchedule(1, 101, "SUN", "07:00", "TUE", "06:00", "12:00", new Date(),
				new Date(), "modifiedBy", new Date(), 1, "TUE", 55, 18, "scheduleType", 103, 1200, 1, 0, "Swathi", null,
				"Apply",1));
		weeklySchedule.add(TestHelper.createWeeklySchedule(1, 102, "SUN", "07:00", "TUE", "06:00", "12:00", new Date(),
				new Date(), "modifiedBy", new Date(), 1, "TUE", 55, 18, "scheduleType", 103, 1200, 2, 1, "Swathi", null,
				"Apply",1));
		weeklySchedule.add(TestHelper.createWeeklySchedule(1, 103, "SUN", "07:00", "TUE", "06:00", "12:00", new Date(),
				new Date(), "modifiedBy", new Date(), 1, "TUE", 55, 18, "scheduleType", 103, 1200, 3, 1, "Swathi", null,
				"Apply",1));
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is not empty
	 */
	@Test
	public void retrieveCurrentScheduleTest() {
		when(deliveryScheduleDAO.findByVendorAndStore(anyInt(), anyInt())).thenReturn(weeklySchedule);

		List<CurrentScheduleRO> list = currentWeekScheduleService.retrieveCurrentSchedule(55, 24);

		assertEquals(3, list.size());
		assertEquals("SUN", list.get(0).getOrderDay());
		assertEquals(new Integer(1), list.get(2).getDsId());
		verify(deliveryScheduleDAO).findByVendorAndStore(anyInt(), anyInt());
	}

	/**
	 * Test case for retrieveCurrentSchedule() for given store id and vendor name
	 * when schedule list is empty
	 */
	@Test
	public void retrieveCurrentScheduleEmptyTest() {
		when(deliveryScheduleDAO.findByVendorAndStore(anyInt(), anyInt())).thenReturn(new ArrayList<WeeklySchedule>());

		List<CurrentScheduleRO> list = currentWeekScheduleService.retrieveCurrentSchedule(55, 24);

		assertTrue(list.isEmpty());
		verify(deliveryScheduleDAO).findByVendorAndStore(anyInt(), anyInt());
	}

}
