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
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.VendorInfoDAO;
import com.raleys.api.cao.odt.schedule.model.VendorInfo;
import com.raleys.api.cao.odt.schedule.representation.VendorRO;
import com.raleys.api.cao.odt.schedule.service.impl.StoreVendorInfoServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for VendorInfoServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class VendorInfoServiceTest {

	@Mock
	private VendorInfoDAO vendorInfoDAO;

	@InjectMocks
	private StoreVendorInfoServiceImpl storeVendorInfoService;

	List<VendorInfo> vendors = new ArrayList<>();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		vendors.add(TestHelper.createVendorInfo(1,"Natomas Meat Tray-Supplies", 80, "Natomas", "Supplies",
				"Meat Trays-Supplies"));
		vendors.add(TestHelper.createVendorInfo(2,"SSI Bakery", 85, "SSI Lathrop", "Bakery", "Frozen-Ice Cream"));
		vendors.add(TestHelper.createVendorInfo(3,"SSI FSD", 90, "SSI Lathrop", "FSD", "Frozen-Ice Cream"));
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
}
