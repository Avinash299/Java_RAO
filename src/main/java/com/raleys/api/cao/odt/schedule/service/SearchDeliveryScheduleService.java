package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.representation.SearchScheduleRO;

public interface SearchDeliveryScheduleService {
	List<SearchScheduleRO> retrieveSchedule(String district,Integer store,String majorDept,int namedScheduleId,AdvancedFilters advancedFilters);
}
