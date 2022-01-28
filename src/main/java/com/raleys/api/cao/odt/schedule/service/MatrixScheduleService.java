package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.representation.MatrixScheduleRO;

public interface MatrixScheduleService {
	List<MatrixScheduleRO> retrieveMasterSchedule(String district, int store);

	List<MatrixScheduleRO> retrieveHolidaySchedule(int holidayId,String district, int store);

}
