package com.raleys.api.cao.odt.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raleys.api.cao.odt.schedule.model.Matrix;

/**
 * @author abhay.thakur
 *
 */
@Repository
public interface MatrixDAO extends JpaRepository<Matrix, Integer> {
	List<Matrix> retrieveMasterScheduleMatrixByDistrictAndStore(String district, int store);

	List<Matrix> retrieveMasterScheduleMatrixByStore(int store);

	List<Matrix> retrieveHolidayScheduleMatrixByDistrictAndStore(int holidayId, String district, int store);

	List<Matrix> retrieveHolidayScheduleMatrixByStore(int holidayId, int store);

}
