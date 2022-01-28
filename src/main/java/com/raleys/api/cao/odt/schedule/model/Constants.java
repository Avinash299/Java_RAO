package com.raleys.api.cao.odt.schedule.model;

/**
 * Constant class for application
 *
 */
public class Constants {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String EFFECTIVEDATEFORMAT = "MM/dd/yyyy";
	public static final String USERNAME = "userName";
	public static final String[] NAMEDSCHEDULESTATUS = { "Master", "Create", "Publish", "Active", "Inactive",
			"Delete" };

	public enum SCHEDULETYPE {
		CURRENT, FUTURE
	}

	public enum NAMEDSCHEDULEACTION {
		UPDATE, PUBLISH, ACTIVE
	}
}
