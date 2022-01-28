package com.raleys.api.cao.odt.schedule.model;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public class Maintenance {
	@Schema(type="String",name="namedSchedule",example="Thanksgiving - 2021")
	@NotBlank(message = "Schedule Name can not be null/blank")
	private String namedSchedule;
	@Schema(type="String",name="effectiveDate",example="11/24/2021")
	@NotBlank(message = "Effective Date can not be null/blank")
	private String effectiveDate;
	@Schema(type="String",name="endDate",example="11/26/2021")
	@NotBlank(message = "End Date  can not be null/blank")
	private String endDate;
	@Schema(type="int",name="holidayId",example="1")
	private int holidayId;
	@Schema(type="String",name="status",example="Create")
	private String status;
	public String getNamedSchedule() {
		return namedSchedule;
	}

	public void setNamedSchedule(String namedSchedule) {
		this.namedSchedule = namedSchedule;
	}

	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 *            the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the holidayId
	 */
	public int getHolidayId() {
		return holidayId;
	}

	/**
	 * @param holidayId the holidayId to set
	 */
	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
	

}
