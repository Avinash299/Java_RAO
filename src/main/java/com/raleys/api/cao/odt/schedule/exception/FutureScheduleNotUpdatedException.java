package com.raleys.api.cao.odt.schedule.exception;

public class FutureScheduleNotUpdatedException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8583012251777791264L;

	/**
	 * @param holidayId
	 */
	public FutureScheduleNotUpdatedException(int dsId) {
        super(" Future Schedule is not updated/logically deleted from database for dsID :" +dsId);
    }
}
