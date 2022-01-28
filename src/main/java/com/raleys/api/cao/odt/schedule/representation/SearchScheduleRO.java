package com.raleys.api.cao.odt.schedule.representation;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhay.thakur
 *
 */
/**
 * @author swathi.kompella
 *
 */
public class SearchScheduleRO {
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
	@Schema(type="String",name="effectiveDate",example="11/24/2021")
	private String effectiveDate;
	@Schema(type="String",name="endDate",example="11/26/2021")
	private String endDate;
	@Schema(type="String",name="district",example="11/26/2021")
	private String district;
	@Schema(type="int",name="store",example="102")
	private int store;
	@Schema(type="String",name="vendor",example="UNFI-Tonys - 2")
	private String vendor;
	@Schema(type="String",name="majorDept",example="Grocery")
	private String majorDept;
	@Schema(type="int",name="dsId",example="123")
	private Integer dsId;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);

	public SearchScheduleRO() {

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
	 * @return the deliveryWindowBegin
	 */
	public String getDeliveryWindowBegin() {
		return deliveryWindowBegin;
	}

	/**
	 * @param deliveryWindowBegin the deliveryWindowBegin to set
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
	 * @param deliveryWindowEnd the deliveryWindowEnd to set
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
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the store
	 */
	public int getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(int store) {
		this.store = store;
	}

	

	
	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * @return the majorDept
	 */
	public String getMajorDept() {
		return majorDept;
	}

	/**
	 * @param majorDept the majorDept to set
	 */
	public void setMajorDept(String majorDept) {
		this.majorDept = majorDept;
	}

	/**
	 * @return the dsId
	 */
	public Integer getDsId() {
		return dsId;
	}

	/**
	 * @param dsId the dsId to set
	 */
	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}


	/**
	 * @param orderDay
	 * @param orderCutoffTime
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param deliveryWindowEnd
	 * @param effectiveDate
	 * @param endDate
	 * @param district
	 * @param store
	 * @param vendor
	 * @param majorDept
	 * @param dsId
	 * @param simpleDateFormat
	 */
	public SearchScheduleRO(String orderDay, String orderCutoffTime, String deliveryDay, String deliveryWindowBegin,
			String deliveryWindowEnd, Date effectiveDate, Date endDate, String district, int store, String vendor,
			String majorDept, Integer dsId) {
		super();
		this.orderDay = orderDay;
		this.orderCutoffTime = orderCutoffTime;
		this.deliveryDay = deliveryDay;
		this.deliveryWindowBegin = deliveryWindowBegin;
		this.deliveryWindowEnd = deliveryWindowEnd;
		if(effectiveDate!=null)
		{
		this.effectiveDate = simpleDateFormat.format(effectiveDate);
		}
		if(endDate!=null)
		{
		this.endDate = simpleDateFormat.format(endDate);
		}
		this.district = district;
		this.store = store;
		this.vendor = vendor;
		this.majorDept = majorDept;
		this.dsId = dsId;
	}

	


	
}
