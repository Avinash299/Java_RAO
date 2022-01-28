package com.raleys.api.cao.odt.schedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.DeliveryScheduleDAO;
import com.raleys.api.cao.odt.schedule.dao.NamedScheduleDAO;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.NamedSchedule;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;
import com.raleys.api.cao.odt.schedule.service.impl.FutureScheduleServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for FutureScheduleServiceImpl.class
 *
 */
@RunWith(SpringRunner.class)
public class FutureScheduleServiceTest {

	@Mock
	private NamedScheduleDAO holidayDAO;

	@Mock
	private DeliveryScheduleDAO deliveryScheduleDAO;

	@Mock
	private NamedScheduleService namedScheduleService;

	@InjectMocks
	private FutureScheduleServiceImpl futureScheduleService;

	List<WeeklySchedule> weeklySchedules = new ArrayList<>();
	DeliverySchedule deliverySchedule;
	NamedSchedule namedSchedule;
	Optional<WeeklySchedule> oWeeklySchedule = Optional.of(
			TestHelper.createWeeklySchedule(5, 105, "TUE", "09:00", "THU", "08:00", "14:00", new Date(), new Date(),
					"user2", null, 1, "TUE", 55, 18, "Current", 103, 1400, 3, 1, "Abhay", new Date(), "Create", 0));
	
	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {

		weeklySchedules.add(TestHelper.createWeeklySchedule(1, 101, "SUN", "07:00", "TUE", "06:00", "12:00", new Date(),
				new Date(), "admin", new Date(), 1, "TUE", 55, 18, "Current", 103, 1200, 1, 1, "Swathi", new Date(),
				"Create", 0));
		weeklySchedules.add(TestHelper.createWeeklySchedule(1, 102, "MON", "08:00", "WED", "07:00", "13:00", new Date(),
				new Date(), "user1", new Date(), 1, "TUE", 55, 18, "Current", 103, 1300, 2, 0, "Swathi", new Date(),
				"Publish", 0));
		weeklySchedules.add(TestHelper.createWeeklySchedule(1, 103, "TUE", "09:00", "THU", "08:00", "14:00", new Date(),
				new Date(), "user2", new Date(), 1, "TUE", 55, 18, "Current", 103, 1400, 3, 1, "Swathi", new Date(),
				"Active", 0));
		weeklySchedules.add(TestHelper.createWeeklySchedule(1, 103, "TUE", "09:00", "THU", "08:00", "14:00", new Date(),
				new Date(), "user2", null, 1, "TUE", 55, 18, "Current", 103, 1400, 3, 1, "Swathi", new Date(), "Create",
				0));

		this.deliverySchedule = TestHelper.createNewSchedule(102, "SUN", "07:00", "TUE", "06:00", "12:00", "TUE", null,
				55, 18, 102, 1, "Create");
		this.namedSchedule = TestHelper.createNamedSchedule(2, "Labour Day - 2021", new Date(), new Date(), "Active",
				new Date(), new Date(), "asit", "admin");

	}

	/**
	 * Test case for retrieveFutureSchedule() when holiday list is not empty
	 */
	@Test
	public void retrieveFutureScheduleTest() {
		when(deliveryScheduleDAO.findByVendorAndStoreAndHoliday(anyInt(), anyInt(), anyInt()))
				.thenReturn(weeklySchedules);

		List<FutureScheduleRO> futureScheduleROs = futureScheduleService.retrieveFutureSchedule(102, 18, 1);
		assertEquals("SUN", futureScheduleROs.get(0).getOrderDay());
		assertEquals("MON", futureScheduleROs.get(1).getOrderDay());

		verify(deliveryScheduleDAO).findByVendorAndStoreAndHoliday(anyInt(), anyInt(), anyInt());
	}

	/**
	 * Test case for retrieveFutureSchedule() when holiday list is empty
	 */
	@Test
	public void retrieveFutureScheduleTestWhenEmpty() {
		when(deliveryScheduleDAO.findByVendorAndStoreAndHoliday(anyInt(), anyInt(), anyInt()))
				.thenReturn(new ArrayList<WeeklySchedule>());

		List<FutureScheduleRO> futureScheduleROs = futureScheduleService.retrieveFutureSchedule(102, 18, 1);

		assertTrue(futureScheduleROs.isEmpty());

		verify(deliveryScheduleDAO).findByVendorAndStoreAndHoliday(anyInt(), anyInt(), anyInt());
	}

	/**
	 * Test case for saveFutureSchedule()
	 */

	@Test
	public void saveFutureScheduleTestWhenNull() {
		when(namedScheduleService.checkByHolidayId(Mockito.anyInt())).thenReturn(namedSchedule);

		assertNull(futureScheduleService.saveFutureSchedule(deliverySchedule, 102, 18, 2, "admin"));
		verify(namedScheduleService).checkByHolidayId(Mockito.anyInt());
	}

	@Test
	public void saveFutureScheduleTestWhenNotNull() {
		namedSchedule.setStatus("Publish");
		when(namedScheduleService.checkByHolidayId(Mockito.anyInt())).thenReturn(namedSchedule);
		when(deliveryScheduleDAO.save(Mockito.any(WeeklySchedule.class))).thenReturn(weeklySchedules.get(0));

		DeliveryScheduleDTO deliveryScheduleDTO = futureScheduleService.saveFutureSchedule(deliverySchedule, 102, 18, 2,
				"admin");

		assertEquals("SUN", deliveryScheduleDTO.getOrderDay());
		verify(deliveryScheduleDAO).save(Mockito.any(WeeklySchedule.class));
	}

	/**
	 * Test case for removeScheduleDay() when variable update==1
	 */
	@Test
	public void removeScheduleDayTestWhenUpdated() {
		when(deliveryScheduleDAO.findById(anyInt())).thenReturn(oWeeklySchedule);
		namedSchedule.setStatus("Publish");
		when(namedScheduleService.checkByHolidayId(Mockito.anyInt())).thenReturn(namedSchedule);
		int response = futureScheduleService.removeScheduleDay("admin", 1);
		assert (response > 0);
		verify(deliveryScheduleDAO).save(Mockito.any(WeeklySchedule.class));
	}

	/**
	 * Test case for removeScheduleDay() when variable update==0
	 */

	@Test
	public void removeScheduleDayTestWhenNotUpdated() {
		when(deliveryScheduleDAO.findById(anyInt())).thenReturn(oWeeklySchedule);
		when(namedScheduleService.checkByHolidayId(Mockito.anyInt())).thenReturn(namedSchedule);
		int response = futureScheduleService.removeScheduleDay("admin", 1);
		assert (response == 0);
	}

	/**
	 * Test case for findById() when namedSchedule not null
	 */

	@Test
	public void checkByDsIdTestWhenNotNull() {
		when(deliveryScheduleDAO.findById(anyInt())).thenReturn(oWeeklySchedule);
		WeeklySchedule weeklyScheduleO = futureScheduleService.checkByDsId(1);
		assertEquals(5,(int)weeklyScheduleO.getDsId());
		verify(deliveryScheduleDAO).findById(anyInt());
	}

	/**
	 * Test case for findById() when namedSchedule null
	 */

	@Test
	public void checkByDsIdTestWhenNull() {
		Optional<WeeklySchedule> weOptional = Optional.ofNullable(null);
		when(deliveryScheduleDAO.findById(anyInt())).thenReturn(weOptional);
		WeeklySchedule weeklyScheduleO = futureScheduleService.checkByDsId(1);
		assertNull(weeklyScheduleO);
		verify(deliveryScheduleDAO).findById(anyInt());
	}
}
