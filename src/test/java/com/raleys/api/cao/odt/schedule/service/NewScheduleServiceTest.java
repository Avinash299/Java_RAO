package com.raleys.api.cao.odt.schedule.service;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.DeliveryScheduleDAO;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.Constants.SCHEDULETYPE;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.service.impl.NewScheduleServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for NewScheduleServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class NewScheduleServiceTest {

	@Mock
	private DeliveryScheduleDAO deliveryScheduleDAO;

	@Mock
	private DateFormat simpleDateFormat;

	@InjectMocks
	private NewScheduleServiceImpl newScheduleService;

	@Captor
	private ArgumentCaptor<List<WeeklySchedule>> argumentCaptor;

	List<DeliverySchedule> newSchedules = new ArrayList<>();
	List<WeeklySchedule> weeklySchedules = new ArrayList<>();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		newSchedules.add(TestHelper.createNewSchedule(102, "SUN", "07:00", "TUE", "06:00", "12:00", "07/07/2021", "TUE",
				55, 18, 102, 10, "Apply"));
		newSchedules.add(TestHelper.createNewSchedule(102, "TUE", "08:00", "THU", "09:00", "11:00", "08/08/2021", "TUE",
				56, 19, 102, 0, "Apply"));

		mapNewSchedulesToWeeklySchedules();
	}

	/**
	 * Test case for saveAllNewSchedule() for given schedule list,store id and
	 * vendor name
	 */
	@Test
	public void saveAllNewScheduleTest() {
		when(deliveryScheduleDAO.saveAll(anyList())).thenReturn(weeklySchedules);

		List<DeliveryScheduleDTO> newScheduleDTOs = newScheduleService.saveAllNewSchedule(newSchedules, 102, 1, "user");

		verify(deliveryScheduleDAO).saveAll(argumentCaptor.capture());
		assertEquals("CURRENT", argumentCaptor.getValue().get(0).getScheduleType());
		assertEquals("TUE", newScheduleDTOs.get(0).getOrderDay());
		assertEquals(102, newScheduleDTOs.get(0).getDsId());
	}

	/**
	 * Test case for saveAllNewSchedule() for given schedule list,store id and
	 * vendor name
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void saveAllNewScheduleTestWithException() throws Exception {
		when(simpleDateFormat.parse(anyString())).thenThrow(new ParseException("Parse Exception", 0));

		newScheduleService.saveAllNewSchedule(newSchedules, 102, 1, "admin");

	}

	/**
	 * Mapping new schedule list to weekly schedule list
	 */
	public void mapNewSchedulesToWeeklySchedules() {
		DateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);
		this.newSchedules.forEach(ns -> {
			try {
				this.weeklySchedules.add(new WeeklySchedule(ns.getDsId(), 102, 1,
						simpleDateFormat.parse(ns.getEffectiveDate()), ns.getOrderDay(), ns.getDeliveryDay(),
						ns.getDeliveryWindowBegin(), SCHEDULETYPE.CURRENT.toString(), ns.getDeliveryWindowEnd(),
						ns.getOrderCutoffTime(), ns.getOnShelfDay(), ns.getOnShelfHours(), ns.getDeliveryHour(),
						ns.getCreateLeadTime(), "admin", ns.getDeleteFlag()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
	}

}
