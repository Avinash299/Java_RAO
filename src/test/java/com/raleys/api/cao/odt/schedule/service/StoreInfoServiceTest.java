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
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.raleys.api.cao.odt.schedule.dao.StoreInfoDAO;
import com.raleys.api.cao.odt.schedule.model.StoreInfo;
import com.raleys.api.cao.odt.schedule.representation.StoreInfoRO;
import com.raleys.api.cao.odt.schedule.service.impl.StoreVendorInfoServiceImpl;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/**
 * Test cases for StoreInfoServiceImpl.class
 */
@RunWith(SpringRunner.class)
public class StoreInfoServiceTest {

	@Mock
	private StoreInfoDAO storeInfoDAO;

	@InjectMocks
	private StoreVendorInfoServiceImpl storeVendorInfoService;

	List<StoreInfo> stores = new ArrayList<>();

	/**
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		stores.add(TestHelper.createStoreInfo(102, "District 1"));
		stores.add(TestHelper.createStoreInfo(103, "District 2"));
		stores.add(TestHelper.createStoreInfo(105, "Food Source"));
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
}
