package com.raleys.api.cao.odt.schedule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;


@Entity
@Table(name = "cao_weeklyschedule", schema = "WHOWN")
@SecondaryTable(name = "cao_store", schema = "WHOWN")
@SecondaryTable(name = "cao_vendor", schema = "WHOWN")
@NamedNativeQuery(name = "Matrix.retrieveMasterScheduleMatrixByDistrictAndStore", query = "SELECT ws.*,cv.major_dept,cv.vendor_name from cao_weeklyschedule ws, cao_vendor cv,cao_store cs WHERE ws.vendor_id=cv.vendor_id and ws.store_id=cs.store_id AND ws.create_date BETWEEN (select TRUNC(sysdate, 'w') AS start_date from dual) AND (select TRUNC(sysdate, 'w') + 7 - 1/86400 AS end_date from dual) AND SCHEDULE_TYPE='CURRENT' AND DS_DELETION=0 AND cs.district =?  AND ws.store_id=?  ORDER BY CASE WHEN order_day = 'Sunday' THEN 1 WHEN order_day = 'Monday' THEN 2 WHEN order_day = 'Tuesday' THEN 3 WHEN order_day = 'Wednesday' THEN 4 WHEN order_day = 'Thursday' THEN 5 WHEN order_day = 'Friday' THEN 6 WHEN order_day = 'Saturday' THEN 7 END ASC", resultClass = Matrix.class)
@NamedNativeQuery(name = "Matrix.retrieveMasterScheduleMatrixByStore", query = "SELECT ws.*,cv.major_dept,cv.vendor_name from cao_weeklyschedule ws, cao_vendor cv WHERE ws.vendor_id=cv.vendor_id and ws.create_date BETWEEN (select TRUNC(sysdate, 'w') AS start_date from dual) AND (select TRUNC(sysdate, 'w') + 7 - 1/86400 AS end_date from dual) AND SCHEDULE_TYPE='CURRENT' AND DS_DELETION=0  AND ws.store_id=?  ORDER BY CASE WHEN order_day = 'Sunday' THEN 1 WHEN order_day = 'Monday' THEN 2 WHEN order_day = 'Tuesday' THEN 3 WHEN order_day = 'Wednesday' THEN 4 WHEN order_day = 'Thursday' THEN 5 WHEN order_day = 'Friday' THEN 6 WHEN order_day = 'Saturday' THEN 7 END ASC", resultClass = Matrix.class)
@NamedNativeQuery(name = "Matrix.retrieveHolidayScheduleMatrixByDistrictAndStore", query = "SELECT ws.*,cv.major_dept,cv.vendor_name from cao_weeklyschedule ws, cao_vendor cv,cao_store cs WHERE ws.vendor_id=cv.vendor_id and ws.store_id=cs.store_id  AND SCHEDULE_TYPE='FUTURE' AND DS_DELETION=0 AND ws.NAMED_SCHEDULE_ID=? AND cs.district =?  AND ws.store_id=?  ORDER BY CASE WHEN order_day = 'Sunday' THEN 1 WHEN order_day = 'Monday' THEN 2 WHEN order_day = 'Tuesday' THEN 3 WHEN order_day = 'Wednesday' THEN 4 WHEN order_day = 'Thursday' THEN 5 WHEN order_day = 'Friday' THEN 6 WHEN order_day = 'Saturday' THEN 7 END ASC", resultClass = Matrix.class)
@NamedNativeQuery(name = "Matrix.retrieveHolidayScheduleMatrixByStore", query = "SELECT ws.*,cv.major_dept,cv.vendor_name from cao_weeklyschedule ws, cao_vendor cv WHERE ws.vendor_id=cv.vendor_id  AND SCHEDULE_TYPE='FUTURE' AND DS_DELETION=0 AND ws.NAMED_SCHEDULE_ID=? AND ws.store_id=?  ORDER BY CASE WHEN order_day = 'Sunday' THEN 1 WHEN order_day = 'Monday' THEN 2 WHEN order_day = 'Tuesday' THEN 3 WHEN order_day = 'Wednesday' THEN 4 WHEN order_day = 'Thursday' THEN 5 WHEN order_day = 'Friday' THEN 6 WHEN order_day = 'Saturday' THEN 7 END ASC", resultClass = Matrix.class)
public class Matrix {
	@Id
	@Column(name = "DS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int searchDsId;
	@Column(name = "ORDER_DAY")
	private String searchOrderDay;
	@Column(name = "ORDER_CUTOFF_TIME")
	private String searchOrderCutoffTime;
	@Column(name = "DELIVERY_WINDOW_BEGIN")
	private String searchDeliveryBegin;
	@Column(name = "DELIVERY_WINDOW_END")
	private String searchDeliveryEnd;
	@Column(name = "VENDOR_NAME", table = "cao_vendor")
	private String searchVendorName;
	@Column(name = "MAJOR_DEPT", table = "cao_vendor")
	private String searchMajorDept;

	/**
	 * @return the searchDsId
	 */
	public Integer getSearchDsId() {
		return searchDsId;
	}

	/**
	 * @param searchDsId the searchDsId to set
	 */
	public void setSearchDsId(Integer searchDsId) {
		this.searchDsId = searchDsId;
	}

	/**
	 * @return the searchOrderDay
	 */
	public String getSearchOrderDay() {
		return searchOrderDay;
	}

	/**
	 * @param searchOrderDay the searchOrderDay to set
	 */
	public void setSearchOrderDay(String searchOrderDay) {
		this.searchOrderDay = searchOrderDay;
	}

	/**
	 * @return the searchOrderCutoffTime
	 */
	public String getSearchOrderCutoffTime() {
		return searchOrderCutoffTime;
	}

	/**
	 * @param searchOrderCutoffTime the searchOrderCutoffTime to set
	 */
	public void setSearchOrderCutoffTime(String searchOrderCutoffTime) {
		this.searchOrderCutoffTime = searchOrderCutoffTime;
	}

	/**
	 * @return the searchDeliveryBegin
	 */
	public String getSearchDeliveryBegin() {
		return searchDeliveryBegin;
	}

	/**
	 * @param searchDeliveryBegin the searchDeliveryBegin to set
	 */
	public void setSearchDeliveryBegin(String searchDeliveryBegin) {
		this.searchDeliveryBegin = searchDeliveryBegin;
	}

	/**
	 * @return the searchDeliveryEnd
	 */
	public String getSearchDeliveryEnd() {
		return searchDeliveryEnd;
	}

	/**
	 * @param searchDeliveryEnd the searchDeliveryEnd to set
	 */
	public void setSearchDeliveryEnd(String searchDeliveryEnd) {
		this.searchDeliveryEnd = searchDeliveryEnd;
	}

	/**
	 * @return the searchVendorName
	 */
	public String getSearchVendorName() {
		return searchVendorName;
	}

	/**
	 * @param searchVendorName the searchVendorName to set
	 */
	public void setSearchVendorName(String searchVendorName) {
		this.searchVendorName = searchVendorName;
	}

	/**
	 * @return the searchMajorDept
	 */
	public String getSearchMajorDept() {
		return searchMajorDept;
	}

	/**
	 * @param searchMajorDept the searchMajorDept to set
	 */
	public void setSearchMajorDept(String searchMajorDept) {
		this.searchMajorDept = searchMajorDept;
	}

	/**
	 * @param searchDsId the searchDsId to set
	 */
	public void setSearchDsId(int searchDsId) {
		this.searchDsId = searchDsId;
	}

}
