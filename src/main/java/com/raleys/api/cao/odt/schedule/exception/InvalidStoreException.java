package com.raleys.api.cao.odt.schedule.exception;

public class InvalidStoreException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1190491087458213477L;

	public InvalidStoreException(int store) {
        super(store + " is not a valid store");
    }
}
