package com.raleys.api.cao.odt.schedule.exception;

public class InvalidVendorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886495579243121525L;

	public InvalidVendorException(int vendorId) {
		super(vendorId + " is not a valid vendor");
	}
}
