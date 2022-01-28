package com.raleys.api.cao.odt.schedule.model;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @author abhay.thakur
 *
 */
public class DeliverySchedule {

	@Schema(type="int",name="dsId",example="0")
	private Integer dsId;
	@Schema(type="String",name="orderDay",example="SUNDAY")
	@NotBlank(message = "Order day can not be null/blank")
	private String orderDay;
	@Schema(type="String",name="orderCutoffTime",example="06:00")
	@NotBlank(message = "Order Cutoff Time  can not be null/blank")
	private String orderCutoffTime;
	@Schema(type="String",name="deliveryDay",example="MONDAY")
	@NotBlank(message = "Delivery day can not be null/blank")
	private String deliveryDay;
	@Schema(type="String",name="deliveryWindowBegin",example="07:00")
	@NotBlank(message = "Delivery Window begin can not be null/blank")
	private String deliveryWindowBegin;
	@Schema(type="String",name="deliveryWindowEnd",example="08:00")
	@NotBlank(message = "Delivery Window end can not be null/blank")
	private String deliveryWindowEnd;
	@Schema(type="String",name="effectiveDate",example="10/20/2021")
	private String effectiveDate;
	@Schema(type="String",name="onShelfDay",example="TUESDAY")
	@NotBlank(message = "OnShelf day can not be null/blank")	
	private String onShelfDay;
	@Schema(type="int",name="onShelfHours",example="08:00")
	private Integer onShelfHours;
	@Schema(type="int",name="createLeadTime",example="18")
	private Integer createLeadTime;
	@Schema(type="int",name="deliveryHour",example="08:00")
	private Integer deliveryHour;
	@Schema(type="int",name="deleteFlag",example="0")
	private int deleteFlag;
	@Schema(type="int",name="holidayId",example="1")
	private int holidayId;
	@Schema(type="String",name="status",example="Create")
	private String status;
	
	/**
	 * @return the dsId
	 */
	public Integer getDsId() {
		return dsId;
	}

	/**
	 * @param dsId
	 *            the dsId to set
	 */
	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	/**
	 * @return the orderDay
	 */
	public String getOrderDay() {
		return orderDay;
	}

	/**
	 * @param orderDay
	 *            the orderDay to set
	 */
	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}

	/**
	 * @return the orderCutoffTime
	 */
	public String getOrderCutoffTime() {
		return orderCutoffTime;
	}

	/**
	 * @param orderCutoffTime
	 *            the orderCutoffTime to set
	 */
	public void setOrderCutoffTime(String orderCutoffTime) {
		this.orderCutoffTime = orderCutoffTime;
	}

	/**
	 * @return the deliveryDay
	 */
	public String getDeliveryDay() {
		return deliveryDay;
	}

	/**
	 * @param deliveryDay
	 *            the deliveryDay to set
	 */
	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	/**
	 * @return the deliveryWindowBegin
	 */
	public String getDeliveryWindowBegin() {
		return deliveryWindowBegin;
	}

	/**
	 * @param deliveryWindowBegin
	 *            the deliveryWindowBegin to set
	 */
	public void setDeliveryWindowBegin(String deliveryWindowBegin) {
		this.deliveryWindowBegin = deliveryWindowBegin;
	}

	/**
	 * @return the deliveryWindowEnd
	 */
	public String getDeliveryWindowEnd() {
		return deliveryWindowEnd;
	}

	/**
	 * @param deliveryWindowEnd
	 *            the deliveryWindowEnd to set
	 */
	public void setDeliveryWindowEnd(String deliveryWindowEnd) {
		this.deliveryWindowEnd = deliveryWindowEnd;
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
	 * @return the onShelfDay
	 */
	public String getOnShelfDay() {
		return onShelfDay;
	}

	/**
	 * @param onShelfDay
	 *            the onShelfDay to set
	 */
	public void setOnShelfDay(String onShelfDay) {
		this.onShelfDay = onShelfDay;
	}

	/**
	 * @return the onShelfHours
	 */
	public Integer getOnShelfHours() {
		return onShelfHours;
	}

	/**
	 * @param onShelfHours
	 *            the onShelfHours to set
	 */
	public void setOnShelfHours(Integer onShelfHours) {
		this.onShelfHours = onShelfHours;
	}

	/**
	 * @return the createLeadTime
	 */
	public Integer getCreateLeadTime() {
		return createLeadTime;
	}

	/**
	 * @param createLeadTime
	 *            the createLeadTime to set
	 */
	public void setCreateLeadTime(Integer createLeadTime) {
		this.createLeadTime = createLeadTime;
	}

	/**
	 * @return the deliveryHour
	 */
	public Integer getDeliveryHour() {
		return deliveryHour;
	}

	/**
	 * @param deliveryHour
	 *            the deliveryHour to set
	 */
	public void setDeliveryHour(Integer deliveryHour) {
		this.deliveryHour = deliveryHour;
	}


	
	

	/**
	 * @return the deleteFlag
	 */
	public int getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	
	

}
