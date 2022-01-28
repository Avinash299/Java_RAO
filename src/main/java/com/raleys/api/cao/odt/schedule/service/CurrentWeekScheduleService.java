package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;

public interface CurrentWeekScheduleService {
	List<CurrentScheduleRO> retrieveCurrentSchedule(int storeId, int vendorId);
}
