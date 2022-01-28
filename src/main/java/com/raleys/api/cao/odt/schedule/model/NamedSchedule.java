package com.raleys.api.cao.odt.schedule.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * abhay.thakur
 * 
 * Entity class for Named Schedule list
 *
 */
@Entity
@Table(name = "CAO_NAMEDSCHEDULE", schema = "whown")
public class NamedSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NAMED_SCHEDULE_ID")
	private int id;
	@Column(name = "NAMED_SCHEDULE")
	private String name;
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	@Column(name = "END_DATE")
	private Date endDate;
	@Column(name = "STATUS")
	private String status;
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", updatable = false)
	private Date createdDate;
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFIED_DATE", insertable = false)
	private Date modifiedDate;
	@Column(name = "MODIFIED_BY", insertable = false)
	private String modifiedBy;
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	/**
	 * 
	 */
	public NamedSchedule() {
	}

	/**
	 * @param name
	 * @param createdBy
	 */
	public NamedSchedule(String name, String createdBy) {
		this.name = name;
		this.createdBy = createdBy;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate
	 *            the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	
	@PrePersist
	public void onCreate() {
		createdDate = new Date();
	}

	@PreUpdate
	public void onUpdate() {
		modifiedDate = new Date();
	}
}
