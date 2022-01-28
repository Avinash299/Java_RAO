package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;

/**
 * @author swathi.kompella Interface for future schedule api
 *
 */
public interface FutureScheduleService {

	List<FutureScheduleRO> retrieveFutureSchedule(int storeId, int vendorId, int holidayId);

	DeliveryScheduleDTO saveFutureSchedule(DeliverySchedule deliverySchedule, int storeId, int vendorId, int holidayId,
			String userName);

	int removeScheduleDay(String userName, int dsIds);

	WeeklySchedule checkByDsId(int dsId);
}
