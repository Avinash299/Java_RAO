package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representation class for Vendor api
 *
 */
public class VendorRO {
	
	@Schema(type="String",name="vendor",example="UNFI-Tonys")
	private String vendor;
	@Schema(type="int",name="vendorId",example="1")
	private int vendorId;

	public VendorRO() {
	}

	

	/**
	 * @param vendor
	 * @param vendorId
	 */
	public VendorRO(String vendor, int vendorId) {
		super();
		this.vendor = vendor;
		this.vendorId = vendorId;
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
