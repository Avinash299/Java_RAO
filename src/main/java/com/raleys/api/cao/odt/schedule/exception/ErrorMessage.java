package com.raleys.api.cao.odt.schedule.exception;

/**
 * @author abhay.thakur
 *
 */
public class ErrorMessage {
    private int statusCode;
    private String message;
    
    
    /**
     * default constructor
     */
    public ErrorMessage()
    {
    	
    }

    /**
     * Parameterized constructor
     * @param statusCode
     * @param timestamp
     * @param message
     */
    public ErrorMessage(int statusCode, String message) {
	this.statusCode=statusCode;
	this.message=message;

    }
    
   

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
	return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
    }


    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
	this.message = message;
    }

	
}
