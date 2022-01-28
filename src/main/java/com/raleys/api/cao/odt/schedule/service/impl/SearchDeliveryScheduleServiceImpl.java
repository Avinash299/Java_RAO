package com.raleys.api.cao.odt.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.ScheduleDAO;
import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.model.Schedule;
import com.raleys.api.cao.odt.schedule.predicates.SchedulePredicates;
import com.raleys.api.cao.odt.schedule.representation.SearchScheduleRO;
import com.raleys.api.cao.odt.schedule.service.SearchDeliveryScheduleService;

@Service(value = "searchDeliveryScheduleService")
public class SearchDeliveryScheduleServiceImpl implements SearchDeliveryScheduleService {
	Logger logger = LoggerFactory.getLogger(SearchDeliveryScheduleServiceImpl.class);

	@Autowired
	private ScheduleDAO scheduleDAO;
	@Autowired
	private SchedulePredicates schedulePredicates;

	List<Schedule> weeklySchedules = null;

	@Override
	public List<SearchScheduleRO> retrieveSchedule(String district, Integer store, String majorDept,
			int namedScheduleId, AdvancedFilters advancedFilters) {

		weeklySchedules = new ArrayList<>();
		if (district != null) {
			if (store == null) {
				logger.info("check retrieveScheduleByDistrict:");
				scheduleDAO.retrieveScheduleByDistrict(district, majorDept, namedScheduleId).iterator()
						.forEachRemaining(weeklySchedules::add);
			} else {
				logger.info("check retrieveScheduleByDistrictAndStore:");
				scheduleDAO.retrieveScheduleByDistrictAndStore(district, store, majorDept, namedScheduleId).iterator()
						.forEachRemaining(weeklySchedules::add);
			}
		} else if (store != null) {
			logger.info("check retrieveScheduleByStore:");
			scheduleDAO.retrieveScheduleByStore(store, majorDept, namedScheduleId).iterator()
					.forEachRemaining(weeklySchedules::add);
		}
		if (advancedFilters != null) {
			weeklySchedules = schedulePredicates.checkAllAdvancedFilters(weeklySchedules, advancedFilters);
		}
		return weeklySchedules.stream()
				.map(ds -> new SearchScheduleRO(ds.getSearchOrderDay(), ds.getSearchOrderCutoffTime(),
						ds.getSearchDeliveryDay(), ds.getSearchDeliveryBegin(), ds.getSearchDeliveryEnd(),
						ds.getSearchEffectiveDate(), ds.getSearchEndDate(), ds.getSearchDistrict(), ds.getStore(),
						ds.getSearchVendorName() + " " + "-" + " " + ds.getSearchSupplier(), majorDept,
						ds.getSearchDsId()))
				.collect(Collectors.toList());

	}
}
