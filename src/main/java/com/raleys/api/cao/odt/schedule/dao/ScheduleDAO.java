package com.raleys.api.cao.odt.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raleys.api.cao.odt.schedule.model.Schedule;

/**
 * @author abhay.thakur
 *
 */
@Repository
public interface ScheduleDAO extends JpaRepository<Schedule, Integer> {

	List<Schedule> retrieveScheduleByDistrict(String district, String majorDept, int namedScheduleId);

	List<Schedule> retrieveScheduleByDistrictAndStore(String district, int storeId, String majorDept,
			int namedScheduleId);

	List<Schedule> retrieveScheduleByStore(int storeId, String majorDept, int namedScheduleId);
}
