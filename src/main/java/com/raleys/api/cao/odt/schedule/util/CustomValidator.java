/**
 * 
 */
package com.raleys.api.cao.odt.schedule.util;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.raleys.api.cao.odt.schedule.exception.InvalidHolidayException;
import com.raleys.api.cao.odt.schedule.exception.InvalidRequestException;
import com.raleys.api.cao.odt.schedule.exception.InvalidStoreException;
import com.raleys.api.cao.odt.schedule.exception.InvalidVendorException;

/**
 * @author swathi.kompella
 *
 */
public class CustomValidator {

	Logger logger = LoggerFactory.getLogger(CustomValidator.class);

	/**
	 * @param storeId
	 * @param vendorId
	 */
	public void validateStoreandVendor(int storeId, int vendorId) {

		if (storeId == 0) {
			logger.info("Invalid StoreId");
			throw new InvalidStoreException(storeId);
		}

		if (vendorId == 0) {
			logger.info("Invalid VendorId ");
			throw new InvalidVendorException(vendorId);
		}
	}

	/**
	 * @param holidayId
	 */
	public void validateHoliday(int holidayId) {
		if (holidayId == 0) {
			logger.info("Invalid holidayId");
			throw new InvalidHolidayException(holidayId);
		}
	}

	/**
	 * @param bindingResult
	 */
	public void validateNewScheduleRequest(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			throw new InvalidRequestException(errors);
		}
	}
}
