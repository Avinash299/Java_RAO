package com.raleys.api.cao.odt.schedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.ScheduleDAO;
import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.model.Schedule;
import com.raleys.api.cao.odt.schedule.predicates.SchedulePredicates;
import com.raleys.api.cao.odt.schedule.representation.SearchScheduleRO;
import com.raleys.api.cao.odt.schedule.service.impl.SearchDeliveryScheduleServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for SearchDeliveryScheduleServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class SearchDeliveryScheduleServiceTest {

	@Mock
	private ScheduleDAO scheduleDAO;

	@Mock
	private SchedulePredicates schedulePredicates;

	@InjectMocks
	private SearchDeliveryScheduleServiceImpl searchDeliveryScheduleService;

	List<Schedule> weeklySchedules = new ArrayList<>();
	AdvancedFilters advancedFilters = new AdvancedFilters();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		weeklySchedules.add(TestHelper.createSchedule(1, 102, "MON", "12:00", "WED", "01:00", "02:00", new Date(),
				new Date(), 18, 1, "Natomas", 85, "District 1", "Grocerry", "Source", "minorDept"));
		weeklySchedules.add(TestHelper.createSchedule(2, 103, "TUE", "01:00", "THU", "02:00", "03:00", new Date(),
				new Date(), 19, 2, "SSI Bakery", 86, "District 2", "Bakery", "Source", "minorDept"));
		weeklySchedules.add(TestHelper.createSchedule(3, 104, "WED", "02:00", "FRI", "03:00", "04:00", new Date(),
				new Date(), 20, 3, "SSI FSD", 87, "District 1", "Grocerry", "Source", "minorDept"));
	}

	/**
	 * Test case for retrieveSchedule() when district is not null and store is null
	 */
	@Test
	public void retrieveScheduleTestWhenDistrictNotNullAndStoreNull() {
		weeklySchedules.remove(1);
		when(scheduleDAO.retrieveScheduleByDistrict(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(weeklySchedules);
		when(schedulePredicates.checkAllAdvancedFilters(Mockito.anyList(), Mockito.any(AdvancedFilters.class)))
				.thenReturn(weeklySchedules);
		List<SearchScheduleRO> searchScheduleROs = searchDeliveryScheduleService.retrieveSchedule("District 1", null,
				"Bakery", 1, advancedFilters);

		assertEquals(2, searchScheduleROs.size());
		assertEquals("Natomas - 85", searchScheduleROs.get(0).getVendor());
		assertEquals("SSI FSD - 87", searchScheduleROs.get(1).getVendor());
		verify(scheduleDAO).retrieveScheduleByDistrict(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt());
	}

	/**
	 * Test case for retrieveSchedule() when district is not null and store is not
	 * null
	 */
	@Test
	public void retrieveScheduleTestWhenDistrictNotNullAndStoreNotNull() {
		weeklySchedules.remove(2);
		weeklySchedules.remove(1);
		when(scheduleDAO.retrieveScheduleByDistrictAndStore(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.anyInt())).thenReturn(weeklySchedules);

		List<SearchScheduleRO> searchScheduleROs = searchDeliveryScheduleService.retrieveSchedule("District 1", 102,
				"Bakery", 1, null);

		assertEquals(1, searchScheduleROs.size());
		assertEquals("Natomas - 85", searchScheduleROs.get(0).getVendor());
		verify(scheduleDAO).retrieveScheduleByDistrictAndStore(Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyString(), Mockito.anyInt());
	}

	/**
	 * Test case for retrieveSchedule() when district is null and store is not null
	 */
	@Test
	public void retrieveScheduleTestWhenDistrictNullAndStoreNotNull() {
		weeklySchedules.remove(2);
		weeklySchedules.remove(1);
		when(scheduleDAO.retrieveScheduleByStore(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(weeklySchedules);

		List<SearchScheduleRO> searchScheduleROs = searchDeliveryScheduleService.retrieveSchedule(null, 102, "Bakery",
				1, null);

		assertEquals(1, searchScheduleROs.size());
		assertEquals("Natomas - 85", searchScheduleROs.get(0).getVendor());
		verify(scheduleDAO).retrieveScheduleByStore(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
	}

	/**
	 * Test case for retrieveSchedule() when district is null and store is null
	 */
	@Test
	public void retrieveScheduleTestWhenDistrictNullAndStoreNull() {
		List<SearchScheduleRO> searchScheduleROs = searchDeliveryScheduleService.retrieveSchedule(null, null, "Bakery",
				1, null);

		assertTrue(searchScheduleROs.isEmpty());
	}
}
