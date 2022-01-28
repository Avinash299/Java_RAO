package com.raleys.api.cao.odt.schedule.service.impl;

import static com.raleys.api.cao.odt.schedule.model.Constants.NAMEDSCHEDULESTATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.DeliveryScheduleDAO;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.Constants.SCHEDULETYPE;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;
import com.raleys.api.cao.odt.schedule.service.FutureScheduleService;
import com.raleys.api.cao.odt.schedule.service.NamedScheduleService;

@Service(value = "futureScheduleService")
public class FutureScheduleServiceImpl implements FutureScheduleService {

	@Autowired
	private DeliveryScheduleDAO deliveryScheduleDAO;
	@Autowired
	private NamedScheduleService namedScheduleService;

	Logger logger = LoggerFactory.getLogger(FutureScheduleServiceImpl.class);

	/**
	 * Method to retrieve future schedule for store and vendor and holiday
	 */
	@Override
	public List<FutureScheduleRO> retrieveFutureSchedule(int storeId, int vendorId, int holidayId) {
		List<WeeklySchedule> weeklySchedules = new ArrayList<>();
		deliveryScheduleDAO.findByVendorAndStoreAndHoliday(vendorId, storeId, holidayId).iterator()
				.forEachRemaining(weeklySchedules::add);
		return weeklySchedules.stream()
				.map(ds -> new FutureScheduleRO(ds.getScheduleOrderDay(), ds.getScheduleOrderCutoffTime(),
						ds.getScheduleDeliveryDay(), ds.getDeliveryBegin(), ds.getDeliveryEnd(),
						ds.getScheduleOnShelfDay(), ds.getScheduleOnShelfHours(), ds.getScheduleCreateLeadTime(),
						ds.getScheduleDeliveryHour(), ds.getDsId(), ds.getNamedScheduleId()))
				.collect(Collectors.toList());
	}

	/**
	 * Method to save future schedule for given store and vendor and selected named
	 * schedule
	 */
	@Override
	public DeliveryScheduleDTO saveFutureSchedule(DeliverySchedule deliverySchedule, int storeId, int vendorId,
			int holidayId, String userName) {
		WeeklySchedule weeklySchedule = new WeeklySchedule(deliverySchedule.getDsId(), storeId, vendorId, holidayId,
				deliverySchedule.getOrderDay(), deliverySchedule.getDeliveryDay(),
				deliverySchedule.getDeliveryWindowBegin(), SCHEDULETYPE.FUTURE.toString(),
				deliverySchedule.getDeliveryWindowEnd(), deliverySchedule.getOrderCutoffTime(),
				deliverySchedule.getOnShelfDay(), deliverySchedule.getOnShelfHours(),
				deliverySchedule.getDeliveryHour(), deliverySchedule.getCreateLeadTime(), userName,
				deliverySchedule.getDeleteFlag(), deliverySchedule.getStatus());
		if (namedScheduleService.checkByHolidayId(holidayId).getStatus().equals(NAMEDSCHEDULESTATUS[3])) {
			return null;
		} else {
			weeklySchedule = deliveryScheduleDAO.save(weeklySchedule);
			return new DeliveryScheduleDTO(weeklySchedule.getScheduleOrderDay(), weeklySchedule.getDsId());
		}
	}

	/**
	 * Method to remove schedule day logically from database
	 */
	@Override
	public int removeScheduleDay(String user, int dsId) {
		int updated = 0;
		WeeklySchedule weeklySchedule = checkByDsId(dsId);
		if (weeklySchedule != null) {
			if (namedScheduleService.checkByHolidayId(weeklySchedule.getNamedScheduleId()).getStatus()
					.equals(NAMEDSCHEDULESTATUS[3])) {
			} else {
				weeklySchedule.setScheduleDeleteFlag(1);
				weeklySchedule.setScheduleModifiedBy(user);
				deliveryScheduleDAO.save(weeklySchedule);
				updated = 1;
			}
		}
		return updated;

	}

	/**
	 * Method to check Weekly schedule by ds Id
	 */
	@Override
	public WeeklySchedule checkByDsId(int dsId) {
		Optional<WeeklySchedule> owSchedule = deliveryScheduleDAO.findById(dsId);
		return owSchedule.isPresent() ? owSchedule.get() : null;
	}

}
