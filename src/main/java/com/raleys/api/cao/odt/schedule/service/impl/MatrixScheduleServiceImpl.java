package com.raleys.api.cao.odt.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.MatrixDAO;
import com.raleys.api.cao.odt.schedule.model.Matrix;
import com.raleys.api.cao.odt.schedule.representation.MatrixScheduleRO;
import com.raleys.api.cao.odt.schedule.service.MatrixScheduleService;

@Service(value = "matrixScheduleService")
public class MatrixScheduleServiceImpl implements MatrixScheduleService {
	Logger logger = LoggerFactory.getLogger(MatrixScheduleServiceImpl.class);

	@Autowired
	private MatrixDAO matrixDAO;
	List<Matrix> matrixs = null;

	/**
	 * Method to retrieve Master schedule matrix for store and district
	 */

	@Override
	public List<MatrixScheduleRO> retrieveMasterSchedule(String district, int store) {
		matrixs = new ArrayList<>();
		if (district != null) {
			matrixDAO.retrieveMasterScheduleMatrixByDistrictAndStore(district, store).iterator()
					.forEachRemaining(matrixs::add);
		} else {
			matrixDAO.retrieveMasterScheduleMatrixByStore(store).iterator().forEachRemaining(matrixs::add);
		}
		return matrixs.stream()
				.map(m -> new MatrixScheduleRO(m.getSearchOrderDay(), m.getSearchOrderCutoffTime(),
						m.getSearchDeliveryBegin() + " " + "-" + " " + m.getSearchDeliveryEnd(), m.getSearchMajorDept(),
						m.getSearchVendorName()))
				.collect(Collectors.toList());
	}

	/**
	 * Method to retrieve Holiday schedule matrix for store and district and
	 * holidayId
	 */
	@Override
	public List<MatrixScheduleRO> retrieveHolidaySchedule(int holidayId, String district, int store) {
		matrixs = new ArrayList<>();
		if (district != null) {
			matrixDAO.retrieveHolidayScheduleMatrixByDistrictAndStore(holidayId, district, store).iterator()
					.forEachRemaining(matrixs::add);
		} else {
			matrixDAO.retrieveHolidayScheduleMatrixByStore(holidayId, store).iterator().forEachRemaining(matrixs::add);
		}
		return matrixs.stream()
				.map(m -> new MatrixScheduleRO(m.getSearchOrderDay(), m.getSearchOrderCutoffTime(),
						m.getSearchDeliveryBegin() + " " + "-" + " " + m.getSearchDeliveryEnd(), m.getSearchMajorDept(),
						m.getSearchVendorName()))
				.collect(Collectors.toList());
	}
}
