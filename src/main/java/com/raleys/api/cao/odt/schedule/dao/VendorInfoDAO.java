package com.raleys.api.cao.odt.schedule.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raleys.api.cao.odt.schedule.model.VendorInfo;

@Repository
public interface VendorInfoDAO extends JpaRepository<VendorInfo, Integer>  {


	 List<VendorInfo> findByMajorDept(String majorDept);
	 
	
}
