
package com.raleys.api.cao.odt.schedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
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

import com.raleys.api.cao.odt.schedule.dao.NamedScheduleDAO;
import com.raleys.api.cao.odt.schedule.dto.CloneScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.Constants.NAMEDSCHEDULEACTION;
import com.raleys.api.cao.odt.schedule.model.Maintenance;
import com.raleys.api.cao.odt.schedule.model.NamedSchedule;
import com.raleys.api.cao.odt.schedule.model.ResponseMessage;
import com.raleys.api.cao.odt.schedule.representation.NamedScheduleRO;
import com.raleys.api.cao.odt.schedule.service.impl.NamedScheduleServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test class for NamedScheduleServiceImpl.class
 *
 */

@RunWith(SpringRunner.class)
public class NamedScheduleServiceTest {

	@Mock
	private NamedScheduleDAO namedScheduleDAO;

	@InjectMocks
	private NamedScheduleServiceImpl namedScheduleService;

	@Mock
	private DateFormat simpleDateFormat;

	List<NamedSchedule> namedSchedules = new ArrayList<>();
	Maintenance maintenance;

	/**
	 * initializing common objects required for test cases
	 */

	@Before
	public void setup() {
		namedSchedules.add(TestHelper.createNamedSchedule(1, "Thanksgiving - 2021", new Date(), new Date(), "Publish", null, null,
				"swati", "admin"));
		namedSchedules.add(TestHelper.createNamedSchedule(2, "Labour Day - 2021", null, null, "Active", null, null,
				"asit", "admin"));
		namedSchedules.add(TestHelper.createNamedSchedule(3, "Christmas - 2021", null, null, "Create", null, null,
				"asit", "admin"));
		namedSchedules.add(TestHelper.createNamedSchedule(4, "Master", null, null, null, null, null, "abhay", "admin"));
		namedSchedules
				.add(TestHelper.createNamedSchedule(5, "Master", null, null, "Publish", null, null, "abhay", "admin"));
		this.maintenance = TestHelper.createMaintenance("Christmas - 2021", "23/08/2021", "30/08/2021");
	}

	/**
	 * Test case for retrieveNamedSchedule() when maintenance is true
	 */

	@Test
	public void retrieveNamedScheduleTestWhenMaintenanceTrue() {
		when(namedScheduleDAO.findAll()).thenReturn(namedSchedules);

		List<NamedScheduleRO> namedScheduleROs = namedScheduleService.retrieveNamedSchedule(true);

		assertEquals(1, namedScheduleROs.get(0).getHolidayId());
		assertEquals("Thanksgiving - 2021", namedScheduleROs.get(0).getNamedSchedule());

		verify(namedScheduleDAO).findAll();
	}

	/**
	 * Test case for retrieveNamedSchedule() when maintenance is false
	 */

	@Test
	public void retrieveNamedScheduleTestWhenMaintenanceFalse() {
		when(namedScheduleDAO.findAll()).thenReturn(namedSchedules);

		List<NamedScheduleRO> namedScheduleROs = namedScheduleService.retrieveNamedSchedule(false);

		assertEquals(3, namedScheduleROs.get(1).getHolidayId());
		assertEquals("Christmas - 2021", namedScheduleROs.get(1).getNamedSchedule());

		verify(namedScheduleDAO).findAll();
	}

	/**
	 * Test case for findById() when namedSchedule not null
	 */

	@Test
	public void checkByHolidayIdTestWhenNotNull() {
		Optional<NamedSchedule> namedSchedule = Optional.of(TestHelper.createNamedSchedule(1, "Thanksgiving - 2021",
				null, null, "Publish", null, null, "swati", "admin"));
		when(namedScheduleDAO.findById(anyInt())).thenReturn(namedSchedule);

		NamedSchedule namedScheduleO = namedScheduleService.checkByHolidayId(1);
		assertEquals(1, namedScheduleO.getId());

		verify(namedScheduleDAO).findById(anyInt());
	}

	/**
	 * Test case for findById() when namedSchedule null
	 */

	@Test
	public void checkByHolidayIdTestWhenNull() {
		Optional<NamedSchedule> namedSchedule = Optional.ofNullable(null);
		when(namedScheduleDAO.findById(anyInt())).thenReturn(namedSchedule);

		NamedSchedule namedScheduleO = namedScheduleService.checkByHolidayId(1);
		assertNull(namedScheduleO);

		verify(namedScheduleDAO).findById(anyInt());
	}

	/**
	 * Test case for deleteNamedSchedule()
	 */
	@Test
	public void deleteNamedScheduleTest() {
		Optional<NamedSchedule> namedSchedule = Optional.of(TestHelper.createNamedSchedule(1, "Thanksgiving - 2021",
				null, null, "Create", null, null, "abhay", "admin"));
		when(namedScheduleDAO.findById(anyInt())).thenReturn(namedSchedule);

		int isDelete = namedScheduleService.deleteNamedSchedule(1);

		assertEquals(1, isDelete);
	}

	/**
	 * Test case for cloneNamedSchedule() when namedSchedule is null
	 */
	@Test
	public void cloneNamedScheduleTestWhenNull() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(null);
		CloneScheduleDTO namedSchedule = namedScheduleService.cloneNamedSchedule("Thanksgiving - 2021", "abhay");
		assertEquals("Thanksgiving - 2021", namedSchedule.getNamedSchedule());
		assertEquals("Create", namedSchedule.getStatus());
		verify(namedScheduleDAO).save(any(NamedSchedule.class));
	}

	/**
	 * Test case for cloneNamedSchedule() when namedSchedule is not null
	 */
	@Test
	public void cloneNamedScheduleTestWhenNotNull() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(0));
		CloneScheduleDTO namedSchedule = namedScheduleService.cloneNamedSchedule("Thanksgiving - 2021", "abhay");
		assertNull(namedSchedule);

		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is null
	 */
	@Test
	public void updateNamedScheduleTestWhenNull() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(null);

		String responseMessage = namedScheduleService.updateNamedSchedule("admin",
				NAMEDSCHEDULEACTION.UPDATE.toString(), maintenance);

		assertEquals(ResponseMessage.NAMEDSCHEDULE_NOT_EXIST, responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is create
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsCreate() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(2));
		when(namedScheduleDAO.save(Mockito.any(NamedSchedule.class))).thenReturn(namedSchedules.get(2));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin",
				NAMEDSCHEDULEACTION.UPDATE.toString(), maintenance);

		assertEquals(NAMEDSCHEDULEACTION.UPDATE.toString(), responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
		verify(namedScheduleDAO).save(Mockito.any(NamedSchedule.class));
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is publish and action is Update
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsPublishActionIsUpdate() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(0));
		when(namedScheduleDAO.save(Mockito.any(NamedSchedule.class))).thenReturn(namedSchedules.get(0));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin",
				NAMEDSCHEDULEACTION.UPDATE.toString(), maintenance);

		assertEquals(NAMEDSCHEDULEACTION.UPDATE.toString(), responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
		verify(namedScheduleDAO).save(Mockito.any(NamedSchedule.class));
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is publish and action is not update
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsPublishActionIsNotUpdate() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(0));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin",
				NAMEDSCHEDULEACTION.PUBLISH.toString(), maintenance);

		assertEquals("PUBLISH", responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is active
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsActive() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(1));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin",
				NAMEDSCHEDULEACTION.ACTIVE.toString(), maintenance);

		assertEquals("ACTIVE", responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is active and action is not update
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsActiveActionIsNotUpdate() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(0));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin",
				NAMEDSCHEDULEACTION.ACTIVE.toString(), maintenance);

		assertEquals("ACTIVE", responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is delete
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsDelete() {
		namedSchedules.get(3).setStatus("Delete");
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(3));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin", "Delete", maintenance);

		assertEquals("Delete", responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is create when exception
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateNamedScheduleTestWhenNotNullAndStatusIsCreateException() throws Exception {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(2));
		when(simpleDateFormat.parse(Mockito.anyString())).thenThrow(new ParseException("Exception", 0));
		maintenance.setEffectiveDate(null);
		namedScheduleService.updateNamedSchedule("admin", NAMEDSCHEDULEACTION.UPDATE.toString(), maintenance);

	}

	/**
	 * Test case for updateNamedSchedule() when namedSchedule is not null and
	 * namedSchedule status is active and action is Other
	 */
	@Test
	public void updateNamedScheduleTestWhenNotNullAndStatusIsPublishActionIsOther() {
		when(namedScheduleDAO.findByName(Mockito.anyString())).thenReturn(namedSchedules.get(0));

		String responseMessage = namedScheduleService.updateNamedSchedule("admin", "OTHER", maintenance);

		assertEquals("OTHER", responseMessage);
		verify(namedScheduleDAO).findByName(Mockito.anyString());
	}

}
