package com.raleys.api.cao.odt.schedule.representation;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

public class NamedScheduleRO {

	@Schema(type="int",name="holidayId",example="1")
	private int holidayId;
	@Schema(type="String",name="namedSchedule",example="Thanksgiving - 2021")
	private String namedSchedule;
	@Schema(type="String",name="effectiveDate",example="11/25/2021")
	private String effectiveDate;
	@Schema(type="String",name="endDate",example="11/26/2021")
	private String endDate;
	@Schema(type="String",name="status",example="Create")
	private String status;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);

	/**
	 * Default Constructor
	 */
	public NamedScheduleRO() {

	}

	/**
	 * @param holidayId
	 * @param namedSchedule
	 * @param effectiveDate
	 * @param endDate
	 * @param status
	 */
	public NamedScheduleRO(int holidayId, String namedSchedule, Date effectiveDate, Date endDate, String status) {
		super();
		this.holidayId = holidayId;
		this.namedSchedule = namedSchedule;
		if(null!=effectiveDate)
		{
		this.effectiveDate = simpleDateFormat.format(effectiveDate);
		}
		if(null!=endDate)
		{
		this.endDate = simpleDateFormat.format(endDate);
		}
		this.status = status;
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
	 * @return the namedSchedule
	 */
	public String getNamedSchedule() {
		return namedSchedule;
	}

	/**
	 * @param namedSchedule the namedSchedule to set
	 */
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
	 * @param effectiveDate the effectiveDate to set
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
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
