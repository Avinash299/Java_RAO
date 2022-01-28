package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;

public interface NewScheduleService {
 List<DeliveryScheduleDTO> saveAllNewSchedule(List<DeliverySchedule> newSchedules, int storeId, int vendorId,
			String userName);

}
