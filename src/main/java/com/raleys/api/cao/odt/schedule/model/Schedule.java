package com.raleys.api.cao.odt.schedule.model;

import java.util.Date;

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
@NamedNativeQuery(name = "Schedule.retrieveScheduleByDistrict", query = "Select ws.*,cs.district,cv.major_dept,cv.vendor_name,cv.source,cv.minor_dept,cv.supplier from cao_weeklyschedule  ws,cao_vendor cv,cao_store cs WHERE ws.vendor_id=cv.vendor_id and ws.store_id=cs.store_id  and  cs.district =? AND cv.major_dept=? and ws.named_schedule_id=? and ws.DS_DELETION=0", resultClass = Schedule.class)
@NamedNativeQuery(name = "Schedule.retrieveScheduleByDistrictAndStore", query = "Select ws.*,cs.district,cv.major_dept,cv.vendor_name,cv.source,cv.minor_dept,cv.supplier from cao_weeklyschedule  ws,cao_vendor cv,cao_store cs WHERE ws.vendor_id=cv.vendor_id and ws.store_id=cs.store_id and cs.district =? AND cs.store_id= ? and cv.major_dept=? and ws.named_schedule_id=? and ws.DS_DELETION=0 ", resultClass = Schedule.class)
@NamedNativeQuery(name = "Schedule.retrieveScheduleByStore", query = "Select ws.*,cs.district,cv.major_dept,cv.vendor_name,cv.source,cv.minor_dept,cv.supplier from cao_weeklyschedule  ws,cao_vendor cv,cao_store cs WHERE ws.vendor_id=cv.vendor_id and ws.store_id=cs.store_id and cs.store_id= ? AND cv.major_dept=? and ws.named_schedule_id=? and ws.DS_DELETION=0 ", resultClass = Schedule.class)
public class Schedule {

	@Id
	@Column(name = "DS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer searchDsId;
	@Column(name = "STORE_ID")
	private int store;
	@Column(name = "ORDER_DAY")
	private String searchOrderDay;
	@Column(name = "ORDER_CUTOFF_TIME")
	private String searchOrderCutoffTime;
	@Column(name = "DELIVERY_DAY")
	private String searchDeliveryDay;
	@Column(name = "DELIVERY_WINDOW_BEGIN")
	private String searchDeliveryBegin;
	@Column(name = "DELIVERY_WINDOW_END")
	private String searchDeliveryEnd;
	@Column(name = "EFFECTIVE_DATE")
	private Date searchEffectiveDate;
	@Column(name = "END_DATE")
	private Date searchEndDate;
	@Column(name = "VENDOR_ID")
	private int searchVendorId;
	@Column(name = "NAMED_SCHEDULE_ID")
	private int namedScheduleId;
	@Column(name = "VENDOR_NAME", table = "cao_vendor")
	private String searchVendorName;
	@Column(name = "SUPPLIER", table = "cao_vendor")
	private int searchSupplier;
	@Column(name = "DISTRICT", table = "cao_store")
	private String searchDistrict;
	@Column(name = "MAJOR_DEPT", table = "cao_vendor")
	private String searchMajorDept;
	@Column(name = "SOURCE", table = "cao_vendor")
	private String searchSource;
	@Column(name = "MINOR_DEPT", table = "cao_vendor")
	private String searchMinorDept;

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
	 * @return the searchDeliveryDay
	 */
	public String getSearchDeliveryDay() {
		return searchDeliveryDay;
	}

	/**
	 * @param searchDeliveryDay the searchDeliveryDay to set
	 */
	public void setSearchDeliveryDay(String searchDeliveryDay) {
		this.searchDeliveryDay = searchDeliveryDay;
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
	 * @return the searchEffectiveDate
	 */
	public Date getSearchEffectiveDate() {
		return searchEffectiveDate;
	}

	/**
	 * @param searchEffectiveDate the searchEffectiveDate to set
	 */
	public void setSearchEffectiveDate(Date searchEffectiveDate) {
		this.searchEffectiveDate = searchEffectiveDate;
	}

	/**
	 * @return the searchEndDate
	 */
	public Date getSearchEndDate() {
		return searchEndDate;
	}

	/**
	 * @param searchEndDate the searchEndDate to set
	 */
	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	/**
	 * @return the searchVendorId
	 */
	public int getSearchVendorId() {
		return searchVendorId;
	}

	/**
	 * @param searchVendorId the searchVendorId to set
	 */
	public void setSearchVendorId(int searchVendorId) {
		this.searchVendorId = searchVendorId;
	}

	/**
	 * @return the namedScheduleId
	 */
	public int getNamedScheduleId() {
		return namedScheduleId;
	}

	/**
	 * @param namedScheduleId the namedScheduleId to set
	 */
	public void setNamedScheduleId(int namedScheduleId) {
		this.namedScheduleId = namedScheduleId;
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
	 * @return the searchSupplier
	 */
	public int getSearchSupplier() {
		return searchSupplier;
	}

	/**
	 * @param searchSupplier the searchSupplier to set
	 */
	public void setSearchSupplier(int searchSupplier) {
		this.searchSupplier = searchSupplier;
	}

	/**
	 * @return the searchDistrict
	 */
	public String getSearchDistrict() {
		return searchDistrict;
	}

	/**
	 * @param searchDistrict the searchDistrict to set
	 */
	public void setSearchDistrict(String searchDistrict) {
		this.searchDistrict = searchDistrict;
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
	 * @return the searchSource
	 */
	public String getSearchSource() {
		return searchSource;
	}

	/**
	 * @param searchSource the searchSource to set
	 */
	public void setSearchSource(String searchSource) {
		this.searchSource = searchSource;
	}

	/**
	 * @return the searchMinorDept
	 */
	public String getSearchMinorDept() {
		return searchMinorDept;
	}

	/**
	 * @param searchMinorDept the searchMinorDept to set
	 */
	public void setSearchMinorDept(String searchMinorDept) {
		this.searchMinorDept = searchMinorDept;
	}

}
