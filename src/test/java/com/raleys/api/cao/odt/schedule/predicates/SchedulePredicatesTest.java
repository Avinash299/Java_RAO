
package com.raleys.api.cao.odt.schedule.predicates;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.model.Schedule;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

@RunWith(SpringRunner.class)
public class SchedulePredicatesTest {

	SchedulePredicates schedulePredicates;

	ArrayList<Schedule> schedules = new ArrayList<>();
	AdvancedFilters advancedFilters;

	@Before
	public void setup() throws ParseException {
		this.schedulePredicates = new SchedulePredicates();
		DateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);
		schedules.add(TestHelper.createSchedule(1, 102, "MON", "12:00", "WED", "01:00", "02:00",
				simpleDateFormat.parse("10/11/2021"), simpleDateFormat.parse("10/13/2021"), 18, 1, "Natomas", 85,
				"District 1", "Grocerry", "Source", "minorDept"));
		schedules.add(TestHelper.createSchedule(2, 103, "TUE", "01:00", "THU", "02:00", "03:00",
				simpleDateFormat.parse("10/14/2021"), simpleDateFormat.parse("10/15/2021"), 19, 2, "SSI Bakery", 86,
				"District 2", "Bakery", "Source", "minorDept"));
		schedules.add(TestHelper.createSchedule(3, 104, "WED", "02:00", "FRI", "03:00", "04:00",
				simpleDateFormat.parse("10/16/2021"), simpleDateFormat.parse("10/18/2021"), 20, 3, "SSI FSD", 87,
				"District 1", "Grocerry", "Source", "minorDept"));
	}

	/**
	 * Test case for checkAllAdvancedFilters() when throws exception
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkAllAdvancedFiltersTestWhenException() throws Exception {
		advancedFilters = TestHelper.createAdvancedFilters("Source", "minorDept", 18, "MON", "12:00", "WED", "01:00",
				"02:00", "sfsd", "dssd");
		List<Schedule> scheduleList = schedulePredicates.checkAllAdvancedFilters(schedules, advancedFilters);
		assertTrue(scheduleList.isEmpty());
	}

	/**
	 * Test case for checkAllAdvancedFilters()
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkAllAdvancedFiltersTest() throws Exception {
		advancedFilters = TestHelper.createAdvancedFilters("Source", "minorDept", 18, "MON", "12:00", "WED", "01:00",
				"02:00", "10/10/2021", "10/12/2021");
		List<Schedule> scheduleList = schedulePredicates.checkAllAdvancedFilters(schedules, advancedFilters);
		assertEquals(1, scheduleList.size());
	}

	/**
	 * Test case for checkAllAdvancedFilters()
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkAllAdvancedFiltersTestWhenListEmpty() throws Exception {
		advancedFilters = TestHelper.createAdvancedFilters("Source", "minorDept", 18, "MON", "12:00", "WED", "01:00",
				"02:00", "10/15/2021", "10/18/2021");
		List<Schedule> scheduleList = schedulePredicates.checkAllAdvancedFilters(schedules, advancedFilters);
		assertEquals(0, scheduleList.size());
	}

	@Test
	public void checkAllAdvancedFiltersTestWhenSerchEffectiveDateNull() throws Exception {
		advancedFilters = TestHelper.createAdvancedFilters("Source", "minorDept", 18, "MON", "12:00", "WED", "01:00",
				"02:00", "10/10/2021", "10/12/2021");
		schedules.get(0).setSearchEffectiveDate(null);
		List<Schedule> scheduleList = schedulePredicates.checkAllAdvancedFilters(schedules, advancedFilters);
		assertTrue(scheduleList.isEmpty());
	}

	@Test
	public void checkAllAdvancedFiltersTestWhenNull() throws Exception {
		advancedFilters = TestHelper.createAdvancedFilters(null, null, 0, null, null, null, null, null, null, null);
		List<Schedule> scheduleList = schedulePredicates.checkAllAdvancedFilters(schedules, advancedFilters);
		assertEquals(3, scheduleList.size());
	}

	@Test
	public void checkAllAdvancedFiltersTestWhenEmpty() throws Exception {
		advancedFilters = TestHelper.createAdvancedFilters("", "", 0, "", "", "", "", "", "", "");
		List<Schedule> scheduleList = schedulePredicates.checkAllAdvancedFilters(schedules, advancedFilters);
		assertEquals(3, scheduleList.size());
	}
}
