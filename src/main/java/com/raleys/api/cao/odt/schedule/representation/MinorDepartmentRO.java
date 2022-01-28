package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representation class for Vendor api
 *
 */
public class MinorDepartmentRO {
	@Schema(type="String",name="minorDepartment",example="UNFI-Tonys")
	private String minorDepartment;

	/**
	 * @return the minorDepartment
	 */
	public String getMinorDepartment() {
		return minorDepartment;
	}

	/**
	 * @param minorDepartment the minorDepartment to set
	 */
	public void setMinorDepartment(String minorDepartment) {
		this.minorDepartment = minorDepartment;
	}

	/**
	 * @param minorDepartment
	 */
	public MinorDepartmentRO(String minorDepartment) {
		super();
		this.minorDepartment = minorDepartment;
	}
	
	

}
