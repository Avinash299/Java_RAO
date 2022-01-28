package com.raleys.api.cao.odt.schedule.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * Entity class for store api
 *
 */
@Entity
@Table(name = "cao_store", schema = "whown")
public class StoreInfo {

	@Id
	@Column(name = "STORE_ID")
	private int store;
	@Column(name = "DISTRICT")
	private String district;

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

}
