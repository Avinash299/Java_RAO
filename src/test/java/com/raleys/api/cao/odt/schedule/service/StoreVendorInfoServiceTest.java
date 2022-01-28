package com.raleys.api.cao.odt.schedule.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

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
import com.raleys.api.cao.odt.schedule.service.impl.StoreVendorInfoServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for StoreVendorInfoServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class StoreVendorInfoServiceTest {

	@Mock
	private StoreInfoDAO storeInfoDAO;
	
	@Mock
	private VendorInfoDAO vendorInfoDAO;

	@InjectMocks
	private StoreVendorInfoServiceImpl storeVendorInfoService;

	List<VendorInfo> vendors = new ArrayList<>();
	List<StoreInfo> stores = new ArrayList<>();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		stores.add(TestHelper.createStoreInfo(102, "District 1"));
		stores.add(TestHelper.createStoreInfo(103, "District 2"));
		stores.add(TestHelper.createStoreInfo(105, "Food Source"));
		vendors.add(TestHelper.createVendorInfo(1,"Natomas Meat Tray-Supplies", 80, "Natomas", "Supplies",
				"Meat Trays-Supplies"));
		vendors.add(TestHelper.createVendorInfo(2,"SSI Bakery", 85, "SSI Lathrop", "Bakery", "Frozen-Ice Cream"));
		vendors.add(TestHelper.createVendorInfo(3,"SSI FSD", 90, "SSI Lathrop", "FSD", "Frozen-Ice Cream"));
	}

	/*
	 * Test case for retrieveStores() when store list is not empty
	 */
	@Test
	public void retrieveStoresTest() {
		when(storeInfoDAO.findAll(Sort.by("store"))).thenReturn(stores);

		List<StoreInfoRO> list = storeVendorInfoService.retrieveStores();
		assertEquals(stores.get(1).getStore(), list.get(1).getStoreId());
		verify(storeInfoDAO).findAll(Sort.by("store"));
	}

	/**
	 * Test case for retrieveStores() when store list is empty
	 */
	@Test
	public void retrieveStoresEmptyTest() {
		when(storeInfoDAO.findAll()).thenReturn(new ArrayList<StoreInfo>());

		List<StoreInfoRO> list = storeVendorInfoService.retrieveStores();
		assertTrue(list.isEmpty());
		verify(storeInfoDAO).findAll(Sort.by("store"));
	}
	
	/**
	 * Test case for retireveVendors() when vendor list is not empty
	 */
	@Test
	public void retireveVendorsTest() {
		when(vendorInfoDAO.findAll()).thenReturn(vendors);

		List<VendorRO> list = storeVendorInfoService.retrieveVendors();

		assertEquals(vendors.get(1).getVendorId(), list.get(1).getVendorId());
		verify(vendorInfoDAO).findAll();
	}

	/**
	 * Test case for retireveVendors() when vendor list is empty
	 */
	@Test
	public void retireveVendorsEmptyTest() {
		when(vendorInfoDAO.findAll()).thenReturn(new ArrayList<VendorInfo>());

		List<VendorRO> list = storeVendorInfoService.retrieveVendors();

		assertTrue(list.isEmpty());
		verify(vendorInfoDAO).findAll();
	}
	
	
	/**
	 * Test case for retrieveMajorDepartments()
	 */
	@Test
	public void retrieveMajorDepartmentsTest() {
		when(vendorInfoDAO.findAll()).thenReturn(vendors);
		
		List<MajorDepartmentRO> majorDepartmentROs=storeVendorInfoService.retrieveMajorDepartments();
		
		assertEquals(3, majorDepartmentROs.size());
		assertEquals("Supplies", majorDepartmentROs.get(0).getMajorDepartment());
		assertEquals("Bakery", majorDepartmentROs.get(1).getMajorDepartment());
		verify(vendorInfoDAO).findAll();
	}
	
	/**
	 * Test case for retrieveSource()
	 */
	@Test
	public void retrieveSourceTest() {
		vendors.remove(2);
		vendors.remove(1);
		when(vendorInfoDAO.findByMajorDept(Mockito.anyString())).thenReturn(vendors);
		
		List<SourceRO> sourceROs=storeVendorInfoService.retrieveSource("Natomas");
		
		assertEquals(1, sourceROs.size());
		assertEquals("Natomas", sourceROs.get(0).getSource());
		verify(vendorInfoDAO).findByMajorDept(Mockito.anyString());
	}
	
	/**
	 * Test case for retrieveMinorDepartment()
	 */
	@Test
	public void retrieveMinorDepartmentTest() {
		vendors.remove(0);
		when(vendorInfoDAO.findByMajorDept(Mockito.anyString())).thenReturn(vendors);
		
		List<MinorDepartmentRO> minorDepartmentROs=storeVendorInfoService.retrieveminorDepartment("SSI Lathrop");
		
		assertEquals(1, minorDepartmentROs.size());
		assertEquals("Frozen-Ice Cream", minorDepartmentROs.get(0).getMinorDepartment());
		verify(vendorInfoDAO).findByMajorDept(Mockito.anyString());
	}
	
	/**
	 * Test case for retrieveVendorName()
	 */
	@Test
	public void retrieveVendorNameTest() {
		vendors.remove(0);
		when(vendorInfoDAO.findByMajorDept(Mockito.anyString())).thenReturn(vendors);
		
		List<VendorRO> minorDepartmentROs=storeVendorInfoService.retrieveVendorName("SSI Lathrop");
		
		assertEquals(2, minorDepartmentROs.size());
		assertEquals("SSI Bakery - 85", minorDepartmentROs.get(0).getVendor());
		assertEquals("SSI FSD - 90", minorDepartmentROs.get(1).getVendor());
		verify(vendorInfoDAO).findByMajorDept(Mockito.anyString());
	}
	
	/**
	 * Test case for retrieveDistricts()
	 */
	@Test
	public void retrieveDistrictsTest() {
		when(storeInfoDAO.findAll()).thenReturn(stores);

		List<DistrictRO> districts = storeVendorInfoService.retrieveDistricts();
		assertEquals(3, districts.size());
		assertEquals("District 1", districts.get(0).getDistrict());
		verify(storeInfoDAO).findAll();
	}
	
	/**
	 * Test case for retrieveStoresByDistrict()
	 */
	@Test
	public void retrieveStoresByDistrictTest() {
		stores.remove(2);
		stores.remove(1);
		stores.add(TestHelper.createStoreInfo(108, "District 1"));
		when(storeInfoDAO.findByDistrict(Mockito.anyString())).thenReturn(stores);

		List<StoreInfoRO> districts = storeVendorInfoService.retrieveStoresByDistrict("District 1");
		assertEquals(2, districts.size());
		assertEquals(102, districts.get(0).getStoreId());
		verify(storeInfoDAO).findByDistrict(Mockito.anyString());
	}
}
