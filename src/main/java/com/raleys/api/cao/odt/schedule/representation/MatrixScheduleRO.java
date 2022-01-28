package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhay.thakur
 *
 */
public class MatrixScheduleRO {
	@Schema(type = "String", name = "orderDay", example = "SUNDAY")
	private String orderDay;
	@Schema(type = "String", name = "orderCutoffTime", example = "06:00")
	private String orderCutoffTime;
	@Schema(type = "String", name = "delivery", example = "07:00 - 09:00")
	private String delivery;
	@Schema(type = "String", name = "department", example = "Grocery")
	private String department;
	@Schema(type = "String", name = "vendor", example = "Natmos-Dry Grocery")
	private String vendor;

	public MatrixScheduleRO() {

	}

	public MatrixScheduleRO(String orderDay, String orderCutoffTime, String delivery, String department,
			String vendor) {
		this.orderDay = orderDay;
		this.orderCutoffTime = orderCutoffTime;
		this.delivery = delivery;
		this.department = department;
		this.vendor = vendor;
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
	 * @return the delivery
	 */
	public String getDelivery() {
		return delivery;
	}

	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
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

}
