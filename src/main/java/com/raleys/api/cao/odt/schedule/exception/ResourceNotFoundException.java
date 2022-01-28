package com.raleys.api.cao.odt.schedule.exception;

/**
 * @author abhay.thakur
 *
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
