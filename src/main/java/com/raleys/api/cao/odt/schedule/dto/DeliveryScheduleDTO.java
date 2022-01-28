package com.raleys.api.cao.odt.schedule.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author abhay.thakur
 *
 */
/**
 * @author swathi.kompella
 *
 */
public class DeliveryScheduleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Schema(type="int",name="dsId",example="1351")
	private Integer dsId;
	@Schema(type="String",name="orderDay",example="SUNDAY")
	private String orderDay;

    /**
     *  default constructor
     */
    public  DeliveryScheduleDTO()
    {
    	
    }
    
	/**
	 * @param orderDay
	 * @param dsId
	 */
	public DeliveryScheduleDTO(String orderDay, Integer dsId) {
		this.orderDay = orderDay;
		this.dsId = dsId;
	}
	

	/**
	 * @return the dsId
	 */
	public Integer getDsId() {
		return dsId;
	}

	/**
	 * @param dsId
	 *            the dsId to set
	 */
	public void setDsId(Integer dsId) {
		this.dsId = dsId;
	}

	/**
	 * @return the orderDay
	 */
	public String getOrderDay() {
		return orderDay;
	}

	/**
	 * @param orderDay
	 *            the orderDay to set
	 */
	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}



	
}
