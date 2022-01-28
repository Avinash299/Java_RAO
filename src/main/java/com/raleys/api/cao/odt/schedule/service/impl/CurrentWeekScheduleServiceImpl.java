package com.raleys.api.cao.odt.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.DeliveryScheduleDAO;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.service.CurrentWeekScheduleService;

@Service(value = "currentWeekScheduleService")
public class CurrentWeekScheduleServiceImpl implements CurrentWeekScheduleService {
	Logger logger = LoggerFactory.getLogger(CurrentWeekScheduleServiceImpl.class);

	@Autowired
	private DeliveryScheduleDAO deliveryScheduleDAO;

	/**
	 * Method to retrieve current schedule for store and vendor
	 */
	@Override
	public List<CurrentScheduleRO> retrieveCurrentSchedule(int storeId, int vendorId) {
		List<WeeklySchedule> weeklySchedules = new ArrayList<>();
		deliveryScheduleDAO.findByVendorAndStore(vendorId, storeId).iterator().
				forEachRemaining(weeklySchedules::add);
		return weeklySchedules.stream()
				.map(ds -> new CurrentScheduleRO(ds.getScheduleOrderDay(), ds.getScheduleOrderCutoffTime(), ds.getScheduleDeliveryDay(),
						ds.getDeliveryBegin(), ds.getDeliveryEnd(), ds.getScheduleEffectiveDate(),
						ds.getScheduleOnShelfDay(), ds.getScheduleOnShelfHours(), ds.getScheduleCreateLeadTime(), ds.getScheduleDeliveryHour(),ds.getDsId(),ds.getScheduleDeleteFlag()))
				.collect(Collectors.toList());
		
	}
}
