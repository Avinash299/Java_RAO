package com.raleys.api.cao.odt.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;

/**
 * @author abhay.thakur
 *
 */
@Repository
public interface DeliveryScheduleDAO extends JpaRepository<WeeklySchedule, Integer> {
	/**
	 * Getting list from DB for given store and vendor
	 * 
	 * @param vendor
	 * @param store
	 * 
	 */
	List<WeeklySchedule> findByVendorAndStore(int vendorId, int storeId);

	/**
	 * Getting list from DB for given store and vendor and holiday
	 * 
	 * @param vendor
	 * @param store
	 * @param holiday
	 * 
	 */
	List<WeeklySchedule> findByVendorAndStoreAndHoliday(int vendorId, int storeId, int holidayId);

}
