package com.raleys.api.cao.odt.schedule.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @author swathi.kompella
 *
 */
@Entity
@Table(name = "cao_weeklyschedule", schema = "WHOWN")
@NamedNativeQuery(name = "WeeklySchedule.findByVendorAndStore", query = "SELECT * from WHOWN.cao_weeklyschedule where  create_date BETWEEN (select TRUNC(sysdate, 'w') AS start_date from dual) AND (select TRUNC(sysdate, 'w') + 7 - 1/86400 AS end_date from dual) AND SCHEDULE_TYPE='CURRENT' AND DS_DELETION=0   AND vendor_id=? AND store_id=?  ORDER BY "
		+ "     CASE\r\n" + "          WHEN order_day = 'Sunday' THEN 1\r\n"
		+ "          WHEN order_day = 'Monday' THEN 2\r\n" + "          WHEN order_day = 'Tuesday' THEN 3\r\n"
		+ "          WHEN order_day = 'Wednesday' THEN 4\r\n" + "          WHEN order_day = 'Thursday' THEN 5\r\n"
		+ "          WHEN order_day = 'Friday' THEN 6\r\n" + "          WHEN order_day = 'Saturday' THEN 7\r\n"
		+ "     END ASC", resultClass = WeeklySchedule.class)
@NamedNativeQuery(name = "WeeklySchedule.findByVendorAndStoreAndHoliday", query = "SELECT * from WHOWN.cao_weeklyschedule where  SCHEDULE_TYPE='FUTURE' AND DS_DELETION=0 AND vendor_id=? AND store_id=? AND named_schedule_id=?  ORDER BY "
		+ "     CASE\r\n" + "          WHEN order_day = 'Sunday' THEN 1\r\n"
		+ "          WHEN order_day = 'Monday' THEN 2\r\n" + "          WHEN order_day = 'Tuesday' THEN 3\r\n"
		+ "          WHEN order_day = 'Wednesday' THEN 4\r\n" + "          WHEN order_day = 'Thursday' THEN 5\r\n"
		+ "          WHEN order_day = 'Friday' THEN 6\r\n" + "          WHEN order_day = 'Saturday' THEN 7\r\n"
		+ "     END ASC", resultClass = WeeklySchedule.class)

@DynamicUpdate(true)
public class WeeklySchedule {

	@Id
	@Column(name = "DS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer dsId;
	@Column(name = "STORE_ID")
	private int storeId;
	@Column(name = "ORDER_DAY")
	private String scheduleOrderDay;
	@Column(name = "ORDER_CUTOFF_TIME")
	private String scheduleOrderCutoffTime;
	@Column(name = "DELIVERY_DAY")
	private String scheduleDeliveryDay;
	@Column(name = "DELIVERY_WINDOW_BEGIN")
	private String deliveryBegin;
	@Column(name = "DELIVERY_WINDOW_END")
	private String deliveryEnd;
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", updatable = false)
	private Date scheduleCreateDate;
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_DATE")
	private Date scheduleModifiedDate;
	@Column(name = "MODIFIED_BY", insertable = false)
	private String scheduleModifiedBy;
	@Column(name = "CREATED_BY", updatable = false)
	private String scheduleCreatedBy;
	@Column(name = "EFFECTIVE_DATE")
	private Date scheduleEffectiveDate;
	@Column(name = "END_DATE")
	private Date scheduleEndDate;
	@Column(name = "ON_SHELF_DAY")
	private String scheduleOnShelfDay;
	@Column(name = "ON_SHELF_HOURS")
	private int scheduleOnShelfHours;
	@Column(name = "CREATE_LEAD_TIME")
	private int scheduleCreateLeadTime;
	@Column(name = "SCHEDULE_TYPE")
	private String scheduleType;
	@Column(name = "DELIVERY_HOUR")
	private int scheduleDeliveryHour;
	@Column(name = "VENDOR_ID")
	private int vendorId;
	@Column(name = "NAMED_SCHEDULE_ID")
	private int namedScheduleId;
	@Column(name = "DS_DELETION")
	private int scheduleDeleteFlag;
	@Column(name = "STATUS")
	private String scheduleStatus;
	@Column(name = "DS_APPLY")
	private int dsApply;
	
	

	/**
	 * 
	 */
	public WeeklySchedule() {

	}

	/**
	 * @param dsId
	 * @param storeId
	 * @param vendorId
	 * @param effectiveDate
	 * @param orderDay
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param scheduleType
	 * @param deliveryWindowEnd
	 * @param orderCutOffTime
	 * @param onSelfDay
	 * @param onSelfHours
	 * @param deliveryHours
	 * @param createLeadTime
	 * @param userName
	 * @param deleteFlag
	 */
	public WeeklySchedule(Integer dsId, int storeId, int vendorId, Date effectiveDate, String orderDay,
			String deliveryDay, String deliveryWindowBegin, String scheduleType, String deliveryWindowEnd,
			String orderCutOffTime, String onSelfDay, int onSelfHours, int deliveryHours, int createLeadTime,
			String userName, int deleteFlag) {
		super();
		this.dsId = dsId;
		this.storeId = storeId;
		this.scheduleOrderDay = orderDay;
		this.scheduleOrderCutoffTime = orderCutOffTime;
		this.scheduleDeliveryDay = deliveryDay;
		this.vendorId = vendorId;
		this.deliveryBegin = deliveryWindowBegin;
		this.deliveryEnd = deliveryWindowEnd;
		this.scheduleEffectiveDate = effectiveDate;
		this.scheduleOnShelfDay = onSelfDay;
		this.scheduleOnShelfHours = onSelfHours;
		this.scheduleCreateLeadTime = createLeadTime;
		this.scheduleType = scheduleType;
		this.scheduleDeliveryHour = deliveryHours;
		this.scheduleModifiedBy = userName;
		this.scheduleCreatedBy = userName;
		this.scheduleDeleteFlag = deleteFlag;
		this.scheduleStatus = "Apply";
		this.dsApply = 1;
	}

	/**
	 * @param dsId
	 * @param storeId
	 * @param vendorId
	 * @param effectiveDate
	 * @param orderDay
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param scheduleType
	 * @param deliveryWindowEnd
	 * @param orderCutOffTime
	 * @param onSelfDay
	 * @param onSelfHours
	 * @param deliveryHours
	 * @param createLeadTime
	 * @param userName
	 * @param deleteFlag
	 */
	public WeeklySchedule(Integer dsId, int storeId, int vendorId, int holidayId, String orderDay, String deliveryDay,
			String deliveryWindowBegin, String scheduleType, String deliveryWindowEnd, String orderCutOffTime,
			String onSelfDay, int onSelfHours, int deliveryHours, int createLeadTime, String userName, int deleteFlag,
			String status) {
		super();
		this.dsId = dsId;
		this.storeId = storeId;
		this.namedScheduleId = holidayId;
		this.scheduleOrderDay = orderDay;
		this.scheduleOrderCutoffTime = orderCutOffTime;
		this.scheduleDeliveryDay = deliveryDay;
		this.vendorId = vendorId;
		this.deliveryBegin = deliveryWindowBegin;
		this.deliveryEnd = deliveryWindowEnd;
		this.scheduleOnShelfDay = onSelfDay;
		this.scheduleOnShelfHours = onSelfHours;
		this.scheduleCreateLeadTime = createLeadTime;
		this.scheduleType = scheduleType;
		this.scheduleDeliveryHour = deliveryHours;
		this.scheduleModifiedBy = userName;
		this.scheduleCreatedBy = userName;
		this.scheduleDeleteFlag = deleteFlag;
		this.scheduleStatus = status;
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
	 * @return the storeId
	 */
	public int getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the scheduleOrderDay
	 */
	public String getScheduleOrderDay() {
		return scheduleOrderDay;
	}

	/**
	 * @param scheduleOrderDay the scheduleOrderDay to set
	 */
	public void setScheduleOrderDay(String scheduleOrderDay) {
		this.scheduleOrderDay = scheduleOrderDay;
	}

	/**
	 * @return the scheduleOrderCutoffTime
	 */
	public String getScheduleOrderCutoffTime() {
		return scheduleOrderCutoffTime;
	}

	/**
	 * @param scheduleOrderCutoffTime the scheduleOrderCutoffTime to set
	 */
	public void setScheduleOrderCutoffTime(String scheduleOrderCutoffTime) {
		this.scheduleOrderCutoffTime = scheduleOrderCutoffTime;
	}

	/**
	 * @return the scheduleDeliveryDay
	 */
	public String getScheduleDeliveryDay() {
		return scheduleDeliveryDay;
	}

	/**
	 * @param scheduleDeliveryDay the scheduleDeliveryDay to set
	 */
	public void setScheduleDeliveryDay(String scheduleDeliveryDay) {
		this.scheduleDeliveryDay = scheduleDeliveryDay;
	}

	/**
	 * @return the deliveryBegin
	 */
	public String getDeliveryBegin() {
		return deliveryBegin;
	}

	/**
	 * @param deliveryBegin the deliveryBegin to set
	 */
	public void setDeliveryBegin(String deliveryBegin) {
		this.deliveryBegin = deliveryBegin;
	}

	/**
	 * @return the deliveryEnd
	 */
	public String getDeliveryEnd() {
		return deliveryEnd;
	}

	/**
	 * @param deliveryEnd the deliveryEnd to set
	 */
	public void setDeliveryEnd(String deliveryEnd) {
		this.deliveryEnd = deliveryEnd;
	}

	/**
	 * @return the scheduleCreateDate
	 */
	public Date getScheduleCreateDate() {
		return scheduleCreateDate;
	}

	/**
	 * @param scheduleCreateDate the scheduleCreateDate to set
	 */
	public void setScheduleCreateDate(Date scheduleCreateDate) {
		this.scheduleCreateDate = scheduleCreateDate;
	}

	/**
	 * @return the scheduleModifiedDate
	 */
	public Date getScheduleModifiedDate() {
		return scheduleModifiedDate;
	}

	/**
	 * @param scheduleModifiedDate the scheduleModifiedDate to set
	 */
	public void setScheduleModifiedDate(Date scheduleModifiedDate) {
		this.scheduleModifiedDate = scheduleModifiedDate;
	}

	/**
	 * @return the scheduleModifiedBy
	 */
	public String getScheduleModifiedBy() {
		return scheduleModifiedBy;
	}

	/**
	 * @param scheduleModifiedBy the scheduleModifiedBy to set
	 */
	public void setScheduleModifiedBy(String scheduleModifiedBy) {
		this.scheduleModifiedBy = scheduleModifiedBy;
	}

	/**
	 * @return the scheduleCreatedBy
	 */
	public String getScheduleCreatedBy() {
		return scheduleCreatedBy;
	}

	/**
	 * @param scheduleCreatedBy the scheduleCreatedBy to set
	 */
	public void setScheduleCreatedBy(String scheduleCreatedBy) {
		this.scheduleCreatedBy = scheduleCreatedBy;
	}

	/**
	 * @return the scheduleEffectiveDate
	 */
	public Date getScheduleEffectiveDate() {
		return scheduleEffectiveDate;
	}

	/**
	 * @param scheduleEffectiveDate the scheduleEffectiveDate to set
	 */
	public void setScheduleEffectiveDate(Date scheduleEffectiveDate) {
		this.scheduleEffectiveDate = scheduleEffectiveDate;
	}

	/**
	 * @return the scheduleEndDate
	 */
	public Date getScheduleEndDate() {
		return scheduleEndDate;
	}

	/**
	 * @param scheduleEndDate the scheduleEndDate to set
	 */
	public void setScheduleEndDate(Date scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	/**
	 * @return the scheduleOnShelfDay
	 */
	public String getScheduleOnShelfDay() {
		return scheduleOnShelfDay;
	}

	/**
	 * @param scheduleOnShelfDay the scheduleOnShelfDay to set
	 */
	public void setScheduleOnShelfDay(String scheduleOnShelfDay) {
		this.scheduleOnShelfDay = scheduleOnShelfDay;
	}

	/**
	 * @return the scheduleOnShelfHours
	 */
	public int getScheduleOnShelfHours() {
		return scheduleOnShelfHours;
	}

	/**
	 * @param scheduleOnShelfHours the scheduleOnShelfHours to set
	 */
	public void setScheduleOnShelfHours(int scheduleOnShelfHours) {
		this.scheduleOnShelfHours = scheduleOnShelfHours;
	}

	/**
	 * @return the scheduleCreateLeadTime
	 */
	public int getScheduleCreateLeadTime() {
		return scheduleCreateLeadTime;
	}

	/**
	 * @param scheduleCreateLeadTime the scheduleCreateLeadTime to set
	 */
	public void setScheduleCreateLeadTime(int scheduleCreateLeadTime) {
		this.scheduleCreateLeadTime = scheduleCreateLeadTime;
	}

	/**
	 * @return the scheduleType
	 */
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * @param scheduleType the scheduleType to set
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	/**
	 * @return the scheduleDeliveryHour
	 */
	public int getScheduleDeliveryHour() {
		return scheduleDeliveryHour;
	}

	/**
	 * @param scheduleDeliveryHour the scheduleDeliveryHour to set
	 */
	public void setScheduleDeliveryHour(int scheduleDeliveryHour) {
		this.scheduleDeliveryHour = scheduleDeliveryHour;
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
	 * @return the scheduleStatus
	 */
	public String getScheduleStatus() {
		return scheduleStatus;
	}

	/**
	 * @param scheduleStatus the scheduleStatus to set
	 */
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	/**
	 * @return the dsApply
	 */
	public int getDsApply() {
		return dsApply;
	}

	/**
	 * @param dsApply the dsApply to set
	 */
	public void setDsApply(int dsApply) {
		this.dsApply = dsApply;
	}

	/**
	 * @return the scheduleDeleteFlag
	 */
	public int getScheduleDeleteFlag() {
		return scheduleDeleteFlag;
	}

	/**
	 * @param scheduleDeleteFlag the scheduleDeleteFlag to set
	 */
	public void setScheduleDeleteFlag(int scheduleDeleteFlag) {
		this.scheduleDeleteFlag = scheduleDeleteFlag;
	}

	@PrePersist
	public void onCreate() {
		scheduleCreateDate = new Date();
	}
	@PreUpdate
	public void onUpdate() {
		scheduleModifiedDate = new Date();
	}


	
	

}
