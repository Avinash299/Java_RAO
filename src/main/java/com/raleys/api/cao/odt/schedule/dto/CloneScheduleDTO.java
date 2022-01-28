package com.raleys.api.cao.odt.schedule.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhay.thakur
 *
 */
public class CloneScheduleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Schema(type="int",name="holidayId",example="1")
	private int holidayId;
	@Schema(type="String",name="namedSchedule",example="Thanksgiving - 2021")
	private String namedSchedule;
	@Schema(type="String",name="status",example="Create")
	private String status;

	/**
	 * default constructor
	 */
	public CloneScheduleDTO() {

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



	public CloneScheduleDTO(int holidayId, String namedSchedule, String status) {
		this.holidayId = holidayId;
		this.namedSchedule = namedSchedule;
		this.status = status;
	}

}
