package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

public class MajorDepartmentRO {

	@Schema(type="String",name="majorDepartment",example="Grocery")
	private String majorDepartment;

	/**
	 * @param majorDepartment
	 */
	public MajorDepartmentRO(String majorDepartment) {
		super();
		this.majorDepartment = majorDepartment;
	}

	/**
	 * @return the majorDepartment
	 */
	public String getMajorDepartment() {
		return majorDepartment;
	}

	/**
	 * @param majorDepartment the majorDepartment to set
	 */
	public void setMajorDepartment(String majorDepartment) {
		this.majorDepartment = majorDepartment;
	}
	
	
	
}
