package com.raleys.api.cao.odt.schedule.representation;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;

import java.text.SimpleDateFormat;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhay.thakur
 *
 */
public class FutureScheduleRO {
	@Schema(type="String",name="orderDay",example="SUNDAY")
	private String orderDay;
	@Schema(type="String",name="orderCutoffTime",example="06:00")
	private String orderCutoffTime;
	@Schema(type="String",name="deliveryDay",example="MONDAY")
	private String deliveryDay;
	@Schema(type="String",name="deliveryWindowBegin",example="07:00")
	private String deliveryWindowBegin;
	@Schema(type="String",name="deliveryWindowEnd",example="08:00")
	private String deliveryWindowEnd;
	@Schema(type="String",name="onShelfDay",example="TUESDAY")
	private String onShelfDay;
	@Schema(type="Integer",name="onShelfHours",example="08:00")
	private Integer onShelfHours;
	@Schema(type="Integer",name="createLeadTime",example="18")
	private Integer createLeadTime;
	@Schema(type="Integer",name="deliveryHour",example="08:00")
	private Integer deliveryHour;
	@Schema(type="Integer",name="dsId",example="1351")
	private Integer dsId;
	@Schema(type="Integer",name="holidayId",example="1s")
	private Integer holidayId;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);

	public FutureScheduleRO() {

	}

	public FutureScheduleRO(String orderDay, String orderCutoffTime, String deliveryDay, String deliveryWindowBegin,
			String deliveryWindowEnd, String onShelfDay, Integer onShelfHours,
			Integer createLeadTime, Integer deliveryHour, Integer dsId, Integer holidayId) {
		super();
		this.orderDay = orderDay;
		this.orderCutoffTime = orderCutoffTime;
		this.deliveryDay = deliveryDay;
		this.deliveryWindowBegin = deliveryWindowBegin;
		this.deliveryWindowEnd = deliveryWindowEnd;
		this.onShelfDay = onShelfDay;
		this.onShelfHours = onShelfHours;
		this.createLeadTime = createLeadTime;
		this.deliveryHour = deliveryHour;
		this.dsId = dsId;
		this.holidayId = holidayId;
	}

	/**
	 * @return the orderDay
	 */
	public String getOrderDay() {
		return orderDay;
	}

	/**
	 * @return the holidayId
	 */
	public Integer getHolidayId() {
		return holidayId;
	}

	/**
	 * @param holidayId
	 *            the holidayId to set
	 */
	public void setHolidayId(Integer holidayId) {
		this.holidayId = holidayId;
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

}
