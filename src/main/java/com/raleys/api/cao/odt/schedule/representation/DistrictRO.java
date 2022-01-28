/**
 * 
 */
package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author swathi.kompella
 *
 */
public class DistrictRO {
	@Schema(type="String",name="district",example="District 1")
	private String district;

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
	 * @param district
	 */
	public DistrictRO(String district) {
		super();
		this.district = district;
	}
	
	

}
