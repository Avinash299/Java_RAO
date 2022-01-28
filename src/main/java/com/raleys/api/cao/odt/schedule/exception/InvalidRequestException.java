/**
 * 
 */
package com.raleys.api.cao.odt.schedule.exception;

import java.util.List;

/**
 * @author swathi.kompella
 *
 */
public class InvalidRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7017381055775618318L;
	

	public InvalidRequestException(List<String> errordetails) {
        super(errordetails +":"+ " Validation Failed");
    }

}
