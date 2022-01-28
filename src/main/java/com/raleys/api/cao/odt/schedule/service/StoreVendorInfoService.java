package com.raleys.api.cao.odt.schedule.service;

import java.util.List;

import com.raleys.api.cao.odt.schedule.representation.DistrictRO;
import com.raleys.api.cao.odt.schedule.representation.MajorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.MinorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.SourceRO;
import com.raleys.api.cao.odt.schedule.representation.StoreInfoRO;
import com.raleys.api.cao.odt.schedule.representation.VendorRO;

public interface StoreVendorInfoService {
	List<StoreInfoRO> retrieveStores();
	List<StoreInfoRO> retrieveStoresByDistrict(String district);
	List<VendorRO> retrieveVendors();
	List<MajorDepartmentRO> retrieveMajorDepartments();
	List<DistrictRO> retrieveDistricts();
	List<SourceRO> retrieveSource(String majorDept);
	List<MinorDepartmentRO> retrieveminorDepartment(String minorDept);
	List<VendorRO> retrieveVendorName(String vendorName);


}
