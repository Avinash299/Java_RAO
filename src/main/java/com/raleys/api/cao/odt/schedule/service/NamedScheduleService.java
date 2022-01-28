package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.dto.CloneScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.Maintenance;
import com.raleys.api.cao.odt.schedule.model.NamedSchedule;
import com.raleys.api.cao.odt.schedule.representation.NamedScheduleRO;

/**
 * @author abhay.thakur
 * 
 *         Interface for named schedule api
 *
 */
public interface NamedScheduleService {
	List<NamedScheduleRO> retrieveNamedSchedule(boolean maintenance);

	CloneScheduleDTO cloneNamedSchedule(String name, String user);

	int deleteNamedSchedule(int holidayId);

	NamedSchedule checkByHolidayId(int holidayId);

	String updateNamedSchedule(String user, String action, Maintenance maintenance);
}
