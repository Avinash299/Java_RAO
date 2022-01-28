/**
 * 
 */
package com.raleys.api.cao.odt.schedule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * Entity class for vendor api
 *
 */
@Entity
@Table(name = "cao_vendor", schema = "whown")
public class VendorInfo {

	@Id
	@Column(name="VENDOR_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int vendorId;
	@Column(name = "VENDOR_NAME")
	private String itascaComment;
	@Column(name = "SUPPLIER")
	private int supplier;
	@Column(name = "SOURCE")
	private String vendorSource;
	@Column(name = "MAJOR_DEPT")
	private String majorDept;
	@Column(name = "MINOR_DEPT")
	private String minorDept;
	/**
	 * @return the itascaComment
	 */
	public String getItascaComment() {
		return itascaComment;
	}
	/**
	 * @param itascaComment the itascaComment to set
	 */
	public void setItascaComment(String itascaComment) {
		this.itascaComment = itascaComment;
	}
	/**
	 * @return the supplier
	 */
	public int getSupplier() {
		return supplier;
	}
	/**
	 * @param supplier the supplier to set
	 */
	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}
	/**
	 * @return the vendorSource
	 */
	public String getVendorSource() {
		return vendorSource;
	}
	/**
	 * @param vendorSource the vendorSource to set
	 */
	public void setVendorSource(String vendorSource) {
		this.vendorSource = vendorSource;
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
	
	
	

	
}
