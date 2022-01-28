/**
 * 
 */
package com.raleys.api.cao.odt.schedule.representation;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representation class for store api
 *
 */
public class StoreInfoRO {

	@Schema(type="int",name="storeId",example="102")
    private int storeId;

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

    public StoreInfoRO() {

    }

    /**
     * @param storeId
     */
    public StoreInfoRO(int storeId) {
	super();
	this.storeId = storeId;
    }

}
