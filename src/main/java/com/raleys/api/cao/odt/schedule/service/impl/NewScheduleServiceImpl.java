package com.raleys.api.cao.odt.schedule.service.impl;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.DeliveryScheduleDAO;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.exception.DateParseException;
import com.raleys.api.cao.odt.schedule.model.Constants.SCHEDULETYPE;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.service.NewScheduleService;

@Service(value = "newScheduleService")
public class NewScheduleServiceImpl implements NewScheduleService {
	@Autowired
	private DeliveryScheduleDAO deliveryScheduleDAO;
	DateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);
	Logger logger = LoggerFactory.getLogger(NewScheduleServiceImpl.class);

	/**
	 * Method to save to new schedule for store and vendor
	 */
	public List<DeliveryScheduleDTO> saveAllNewSchedule(List<DeliverySchedule> newSchedules, int storeId, int vendorId,
			String userName) {
		List<WeeklySchedule> weeklySchedules = new ArrayList<>();
		logger.info("mapping new schedules value to weekly schedule.");
		newSchedules.forEach(ns -> {
			try {
				weeklySchedules.add(new WeeklySchedule(ns.getDsId(), storeId, vendorId,
						simpleDateFormat.parse(ns.getEffectiveDate()), ns.getOrderDay(), ns.getDeliveryDay(),
						ns.getDeliveryWindowBegin(), SCHEDULETYPE.CURRENT.toString(), ns.getDeliveryWindowEnd(),
						ns.getOrderCutoffTime(), ns.getOnShelfDay(), ns.getOnShelfHours(), ns.getDeliveryHour(),
						ns.getCreateLeadTime(), userName, ns.getDeleteFlag()));
			} catch (ParseException exception) {
				logger.error("Exception while parsing effective date: ", exception);
				throw new DateParseException(ns.getEffectiveDate());
			}
		});

		return deliveryScheduleDAO.saveAll(weeklySchedules).stream()
				.filter(weeklySchedule -> weeklySchedule.getScheduleDeleteFlag() == 0)
				.map(ds -> new DeliveryScheduleDTO(ds.getScheduleOrderDay(), ds.getDsId())).collect(Collectors.toList());
	}

}
