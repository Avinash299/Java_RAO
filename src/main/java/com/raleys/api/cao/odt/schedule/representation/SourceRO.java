/**
 * 
 */
package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author swathi.kompella
 *
 */
public class SourceRO {
	@Schema(type="String",name="source",example="UNFI-Tonys")
	private String source;

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @param source
	 */
	public SourceRO(String source) {
		super();
		this.source = source;
	}

	
	
	

}
