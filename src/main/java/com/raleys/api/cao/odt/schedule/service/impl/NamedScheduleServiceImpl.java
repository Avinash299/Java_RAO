package com.raleys.api.cao.odt.schedule.service.impl;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;
import static com.raleys.api.cao.odt.schedule.model.Constants.NAMEDSCHEDULESTATUS;

import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_NOT_EXIST;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.NamedScheduleDAO;
import com.raleys.api.cao.odt.schedule.dto.CloneScheduleDTO;
import com.raleys.api.cao.odt.schedule.exception.DateParseException;
import com.raleys.api.cao.odt.schedule.model.Constants.NAMEDSCHEDULEACTION;
import com.raleys.api.cao.odt.schedule.model.Maintenance;
import com.raleys.api.cao.odt.schedule.model.NamedSchedule;
import com.raleys.api.cao.odt.schedule.representation.NamedScheduleRO;
import com.raleys.api.cao.odt.schedule.service.NamedScheduleService;

@Service(value = "namedScheduleService")
public class NamedScheduleServiceImpl implements NamedScheduleService {

	@Autowired
	private NamedScheduleDAO namedScheduleDAO;
	Logger logger = LoggerFactory.getLogger(NamedScheduleServiceImpl.class);
	DateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);

	/**
	 * Method to retrieve named schedule
	 */
	@Override
	public List<NamedScheduleRO> retrieveNamedSchedule(boolean maintenance) {
		List<NamedSchedule> namedSchedules = new ArrayList<>();
		namedScheduleDAO.findAll().iterator().forEachRemaining(namedSchedules::add);
		if (maintenance ) {

			return namedSchedules.stream().map(ns -> new NamedScheduleRO(ns.getId(), ns.getName(),
					ns.getEffectiveDate(), ns.getEndDate(), ns.getStatus())).collect(Collectors.toList());
		}

		else {
			return namedSchedules.stream()
					.filter(hd -> hd.getStatus() != null && (hd.getStatus().equalsIgnoreCase(NAMEDSCHEDULESTATUS[1])
							|| hd.getStatus().equalsIgnoreCase(NAMEDSCHEDULESTATUS[2])
									&& !hd.getName().equalsIgnoreCase(NAMEDSCHEDULESTATUS[0])))
					.map(holiday -> new NamedScheduleRO(holiday.getId(), holiday.getName(), holiday.getEffectiveDate(),
							holiday.getEndDate(), holiday.getStatus()))
					.collect(Collectors.toList());
		}

	}

	/**
	 * Method to clone named schedule
	 */

	public synchronized CloneScheduleDTO cloneNamedSchedule(String name, String user) {
		NamedSchedule ns = null;
		if (namedScheduleDAO.findByName(name) != null) {
			logger.info("Named Schedule already exist.");
			return null;
		} else {
			ns = new NamedSchedule(name, user);
			ns.setStatus(NAMEDSCHEDULESTATUS[1]);
			namedScheduleDAO.save(ns);
			return new CloneScheduleDTO(ns.getId(), ns.getName(), ns.getStatus());

		}
	}
	/**
	 * Method to check named schedule by Holiday Id
	 */
	@Override
	public NamedSchedule checkByHolidayId(int holidayId) {
		Optional<NamedSchedule> oSchedule = namedScheduleDAO.findById(holidayId);
		return oSchedule.isPresent() ? oSchedule.get() : null;
	}

	/**
	 * Method to delete named schedule
	 */
	@Override
	public synchronized int deleteNamedSchedule(int holidayId) {
		int isDelete = 0;
		if (checkByHolidayId(holidayId).getStatus().equalsIgnoreCase(NAMEDSCHEDULESTATUS[1])) {
			namedScheduleDAO.deleteById(holidayId);
			isDelete = 1;
		}
		return isDelete;
	}

	/**
	 * Method to update and publish named schedule
	 */
	public synchronized String updateNamedSchedule(String user, String action, Maintenance maintenance) {
		NamedSchedule namedSchedule = namedScheduleDAO.findByName(maintenance.getNamedSchedule());

		if (namedSchedule != null) {
			namedSchedule.setModifiedBy(user);
			maintenance.setHolidayId(namedSchedule.getId());
			try {
				namedSchedule.setEffectiveDate(simpleDateFormat.parse(maintenance.getEffectiveDate()));
				namedSchedule.setEndDate(simpleDateFormat.parse(maintenance.getEndDate()));
			} catch (ParseException exception) {
				logger.error("Exception while parsing effective date or end date:", exception);
				throw new DateParseException(maintenance.getEffectiveDate(), maintenance.getEndDate());
			}
			if (action.equals(NAMEDSCHEDULEACTION.UPDATE.toString())) {
				namedScheduleDAO.save(namedSchedule);
				maintenance.setStatus(namedSchedule.getStatus());
				return action;
			} else if (action.equals(NAMEDSCHEDULEACTION.PUBLISH.toString())) {

				namedSchedule.setStatus(NAMEDSCHEDULESTATUS[2]);
				namedScheduleDAO.save(namedSchedule);
				maintenance.setStatus(namedSchedule.getStatus());
				return action;

			} else if (action.equals(NAMEDSCHEDULEACTION.ACTIVE.toString())) {

				namedSchedule.setStatus(NAMEDSCHEDULESTATUS[3]);
				namedScheduleDAO.save(namedSchedule);
				maintenance.setStatus(namedSchedule.getStatus());
				return action;

			}
		} else {
			return NAMEDSCHEDULE_NOT_EXIST;
		}
		return action;
	}

}