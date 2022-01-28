package com.raleys.api.cao.odt.schedule.model;

/**
 * @author swathi.kompella
 * 
 *         Constants class for Swagger configurations
 *
 */
public class SwaggerConstants {

	/**
	 * 
	 */
	private SwaggerConstants() {
		super();
	}

	// Authorization
	public static final String AUTHORIZATION = "Authorization";

	// Version

	public static final String VERSION = "1.0";

	// Title
	public static final String TITILE = "Raley's CAO ODT Schedule Microservice";

	// Description

	public static final String DESCRIPTION = "Rest Api's for CAO ODT Schedule";

	// Status codes and Messages
	public static final String STATUS_SUCCESS = "200";
	public static final String STATUS_SUCCESS_PUT = "204";
	public static final String STATUS_UNAUTHORIZED = "401";
	public static final String STATUS_BADREQUEST = "400";
	public static final String STATUS_NOTFOUND = "404";
	public static final String STATUS_INTERNAL_SERVER_ERROR = "500";
	public static final String SUCCESS_OK = "Success|OK";
	public static final String SUCCESS_NOCONTENT = "Success|No Content";
	public static final String BAD_REQUEST = "Bad Request";
	public static final String UNAUTHORIZED = "User is Not Authorized";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String NOT_FOUND = "Store/Vendor not found";

	// Future Schedule
	public static final String REMOVE_FUTURE_SCHEDULE_NOTES = "This method logically deletes the future schedule in database by updating ds_deletion column as 1 ";
	public static final String REMOVE_FUTURE_SCHEDULE_VALUE = "Remove Future Schedule ";
	public static final String RETRIEVE_FUTURE_SCHEDULE_NOTES = "This method gets the future schedule for given store and vendor and holiday ";
	public static final String RETRIEVE_FUTURE_SCHEDULE_VALUE = "retrieveFutureSchedule";
	public static final String SAVE_FUTURE_SCHEDULE_NOTES = "This method creates/update future schedule to database ";
	public static final String SAVE_FUTURE_SCHEDULE_VALUE = "saveFutureSchedule ";

	// New Schedule
	public static final String SAVE_NEW_SCHEDULE_NOTES = "This method creates/update new schedule to database ";
	public static final String SAVE_NEW_SCHEDULE_VALUE = "saveNewSchedule ";

	// Named Schedule
	public static final String RETRIEVE_NAMED_SCHEDULE_NOTES = "This method gets list of Named Schedule";
	public static final String RETRIEVE_NAMED_SCHEDULE_VALUE = "retrieveNamedSchedule";
	public static final String CLONE_NAMED_SCHEDULE_NOTES = "This method is to clone named schedule";
	public static final String CLONE_NAMED_SCHEDULE_VALUE = "cloneNamedSchedule";
	public static final String DELETE_NAMED_SCHEDULE_NOTES = "This method is to delete named schedule";
	public static final String DELETE_NAMED_SCHEDULE_VALUE = "deleteNamedSchedule";
	public static final String UPDATE_NAMED_SCHEDULE_NOTES = "This method is to update named schedule";
	public static final String UPDATE_NAMED_SCHEDULE_VALUE = "updateNamedSchedule";
	public static final String PUBLISH_NAMED_SCHEDULE_NOTES = "This method is to publish named schedule";
	public static final String PUBLISH_NAMED_SCHEDULE_VALUE = "publishNamedSchedule";
	public static final String ACTIVATE_NAMED_SCHEDULE_NOTES = "This method is to activate named schedule";
	public static final String ACTIVATE_NAMED_SCHEDULE_VALUE = "activateNamedSchedule";

	// Current Schedule
	public static final String CURRENT_SCHEDULE_NOTES = "This method gets the current schedule for given store and vendor";
	public static final String CURRENT_SCHEDULE_VALUE = "currentSchedule";

	// Search Schedule

	public static final String SEARCH_SCHEDULE_NOTES = "This method searches and retrieves  the  schedule for given parameters";
	public static final String SEARCH_SCHEDULE_VALUE = "searchSchedule";

	// Store and Vendor
	public static final String STORE_NOTES = "This method gets list of stores info";
	public static final String STORE_VALUE = "retrieveStores";
	public static final String VENDOR_NOTES = "This method gets list of vendor info";
	public static final String VENDOR_VALUE = "retrieveVendors";
	public static final String DISTRICT_VALUE = "retrieveDistricts";
	public static final String DISTRICT_NOTES = "This method gets list of distinct districts";
	public static final String VENDOR_INFO_NOTES = "This method gets list of vendor info like source or minor departments based on query parameter type";
	public static final String VENDOR_INFO_VALUE = "retrieveVendorInfo";
	// Schedule Matrix
		public static final String MASTER_SCHEDULE_MATRIX_NOTES = "This method gets the master schedule matrix for given store and district";
		public static final String MASTER_SCHEDULE_VALUE = "Master Schedule Matrix";
		public static final String HOLIDAY_SCHEDULE_MATRIX_NOTES = "This method gets the holiday schedule matrix for given holidayId and store and district";
		public static final String HOLIDAY_SCHEDULE_VALUE = "Holiday Schedule Matrix";

}
