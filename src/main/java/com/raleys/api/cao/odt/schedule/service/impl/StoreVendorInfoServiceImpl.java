package com.raleys.api.cao.odt.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raleys.api.cao.odt.schedule.dao.StoreInfoDAO;
import com.raleys.api.cao.odt.schedule.dao.VendorInfoDAO;
import com.raleys.api.cao.odt.schedule.model.StoreInfo;
import com.raleys.api.cao.odt.schedule.model.VendorInfo;
import com.raleys.api.cao.odt.schedule.representation.DistrictRO;
import com.raleys.api.cao.odt.schedule.representation.MajorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.MinorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.SourceRO;
import com.raleys.api.cao.odt.schedule.representation.StoreInfoRO;
import com.raleys.api.cao.odt.schedule.representation.VendorRO;
import com.raleys.api.cao.odt.schedule.service.StoreVendorInfoService;

/**
 * Service implementation class for stores api
 *
 */
@Service(value = "storeVendorInfoService")
public class StoreVendorInfoServiceImpl implements StoreVendorInfoService {
	Logger logger = LoggerFactory.getLogger(StoreVendorInfoServiceImpl.class);

	@Autowired
	private StoreInfoDAO storeInfoDAO;
	

	@Autowired
	private VendorInfoDAO vendorInfoDAO;
	
	
	
	
	/**
	 * Method to retrieve stores info
	 */
	@Override
	public List<StoreInfoRO> retrieveStores() {
		 List<StoreInfo> stores = new ArrayList<>();
		 storeInfoDAO.findAll(Sort.by("store").ascending()).iterator().forEachRemaining(stores::add);
		return stores.stream().map(store -> new StoreInfoRO(store.getStore())).collect(Collectors.toList());

	}
	/**
	 * Method to retrieve vendors info
	 */
	@Override
	public List<VendorRO> retrieveVendors() {
		List<VendorInfo> vendors = new ArrayList<>();
		vendorInfoDAO.findAll().iterator().forEachRemaining(vendors::add);
		return vendors.stream().map(vendor -> new VendorRO(vendor.getItascaComment()+" "+"-"+" "+vendor.getSupplier(),vendor.getVendorId())
		).collect(Collectors.toList());

	}
	
	/**
	 * Method to retrieve vendors info
	 */
	@Override
	public List<MajorDepartmentRO> retrieveMajorDepartments() {
		List<VendorInfo> majorDepartments = new ArrayList<>();
		vendorInfoDAO.findAll().iterator().forEachRemaining(majorDepartments::add);
		return majorDepartments.stream().map(majorDept->new MajorDepartmentRO(majorDept.getMajorDept())).filter(distinctByKey(MajorDepartmentRO::getMajorDepartment)).collect(Collectors.toList());

	}
	
	/**
	 * Method to retrieve sources for given major department
	 */
	@Override
	public List<SourceRO> retrieveSource(String majorDept) {
		List<VendorInfo> sources = new ArrayList<>();
		vendorInfoDAO.findByMajorDept(majorDept).iterator().forEachRemaining(sources::add);
		return sources.stream().map(source->new SourceRO(source.getVendorSource())).filter(distinctByKey(SourceRO::getSource)).collect(Collectors.toList());

	}
	
	/**
	 * Method to retrieve minor departments for given major department
	 */
	@Override
	public List<MinorDepartmentRO> retrieveminorDepartment(String majorDept) {
		List<VendorInfo> minorDepartments = new ArrayList<>();
		vendorInfoDAO.findByMajorDept(majorDept).iterator().forEachRemaining(minorDepartments::add);
		return minorDepartments.stream().map(minorDept->new MinorDepartmentRO(minorDept.getMinorDept())).filter(distinctByKey(MinorDepartmentRO::getMinorDepartment)).collect(Collectors.toList());

	}
	/**
	 *  Method to retrieve districts
	 */
	@Override
	public List<DistrictRO> retrieveDistricts() {
		List<StoreInfo> districts = new ArrayList<>();
		storeInfoDAO.findAll().iterator().forEachRemaining(districts::add);
		return districts.stream().map(district -> new DistrictRO(district.getDistrict())).filter(distinctByKey(DistrictRO::getDistrict)).collect(Collectors.toList());
	}
	
    //Utility function
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	
    

	/**
	 * Method to retrieve sources for given major department
	 */
	@Override
	public List<VendorRO> retrieveVendorName(String majorDept) {
		List<VendorInfo> vendors = new ArrayList<>();
		vendorInfoDAO.findByMajorDept(majorDept).iterator().forEachRemaining(vendors::add);
		return vendors.stream().map(vendor->new VendorRO(vendor.getItascaComment()+" "+"-"+" "+vendor.getSupplier(),vendor.getVendorId())).collect(Collectors.toList());

	}
	/**
	 * Method to retrieve stores by district
	 */
	@Override
	public List<StoreInfoRO> retrieveStoresByDistrict(String district) {
		List<StoreInfo> stores = new ArrayList<>();
		 storeInfoDAO.findByDistrict(district).iterator().forEachRemaining(stores::add);
		return stores.stream().map(store -> new StoreInfoRO(store.getStore())).collect(Collectors.toList());
	}
}
