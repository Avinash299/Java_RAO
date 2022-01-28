package com.raleys.api.cao.odt.schedule.exception;

public class DateParseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8583012251777791264L;

	/**
	 * @param effectiveDate
	 * @param endDate
	 */
	public DateParseException(String effectiveDate, String endDate) {
		super("Exception while parsing effective date or end date :" + effectiveDate + " : " + endDate);
	}
	/**
	 * @param effectiveDate
	 * @param endDate
	 */
	public DateParseException(String effectiveDate) {
		super("Exception while parsing effective date :" + effectiveDate);
	}
}
