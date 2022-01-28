package com.raleys.api.cao.odt.schedule.exception;

public class InvalidHolidayException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8583012251777791264L;

	/**
	 * @param holidayId
	 */
	public InvalidHolidayException(int holidayId) {
        super(holidayId + " is not a valid holidayId");
    }
}
