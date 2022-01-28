package com.raleys.api.cao.odt.schedule.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhay.thakur
 *
 */
public class AdvancedFilters {

	@Schema(type="String",name="source",example="UNFI-Tonys")
	private String source;
	@Schema(type="String",name="minorDepartment",example="UNFI-Tonys")
	private String minorDept;
	@Schema(type="int",name="vendorId",example="1")
	private int vendorId;
	@Schema(type="String",name="orderDay",example="SUNDAY")
	private String orderDay;
	@Schema(type="String",name="orderCutoffTime",example="06:00")
	private String orderCutoffTime;
	@Schema(type="String",name="deliveryDay",example="MONDAY")
	private String deliveryDay;
	@Schema(type="String",name="windowBegin",example="07:00")
	private String windowBegin;
	@Schema(type="String",name="windowEnd",example="08:00")
	private String windowEnd;
	@Schema(type="String",name="effectiveDateFrom",example="10/20/2021")
	private String effectiveDateFrom;
	@Schema(type="String",name="effectiveDateTo",example="18/20/2021")
	private String effectiveDateTo;

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the minorDept
	 */
	public String getMinorDept() {
		return minorDept;
	}

	/**
	 * @param minorDept the minorDept to set
	 */
	public void setMinorDept(String minorDept) {
		this.minorDept = minorDept;
	}

	/**
	 * @return the vendorId
	 */
	public int getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the orderDay
	 */
	public String getOrderDay() {
		return orderDay;
	}

	/**
	 * @param orderDay the orderDay to set
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
	 * @param orderCutoffTime the orderCutoffTime to set
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
	 * @param deliveryDay the deliveryDay to set
	 */
	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
	}

	/**
	 * @return the windowBegin
	 */
	public String getWindowBegin() {
		return windowBegin;
	}

	/**
	 * @param windowBegin the windowBegin to set
	 */
	public void setWindowBegin(String windowBegin) {
		this.windowBegin = windowBegin;
	}

	/**
	 * @return the windowEnd
	 */
	public String getWindowEnd() {
		return windowEnd;
	}

	/**
	 * @param windowEnd the windowEnd to set
	 */
	public void setWindowEnd(String windowEnd) {
		this.windowEnd = windowEnd;
	}

	/**
	 * @return the effectiveDateFrom
	 */
	public String getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	/**
	 * @param effectiveDateFrom the effectiveDateFrom to set
	 */
	public void setEffectiveDateFrom(String effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	/**
	 * @return the effectiveDateTo
	 */
	public String getEffectiveDateTo() {
		return effectiveDateTo;
	}

	/**
	 * @param effectiveDateTo the effectiveDateTo to set
	 */
	public void setEffectiveDateTo(String effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

}
