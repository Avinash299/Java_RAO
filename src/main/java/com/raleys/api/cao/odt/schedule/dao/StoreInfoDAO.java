package com.raleys.api.cao.odt.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raleys.api.cao.odt.schedule.model.StoreInfo;

@Repository
public interface StoreInfoDAO extends JpaRepository<StoreInfo, Integer> {
	
List<StoreInfo> findByDistrict(String district);
	
}
