package com.raleys.api.cao.odt.schedule.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.raleys.api.cao.odt.schedule.representation.DistrictRO;
import com.raleys.api.cao.odt.schedule.representation.MajorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.MinorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.SourceRO;
import com.raleys.api.cao.odt.schedule.representation.StoreInfoRO;
import com.raleys.api.cao.odt.schedule.representation.VendorRO;
import com.raleys.api.cao.odt.schedule.service.StoreVendorInfoService;
import com.raleys.api.cao.odt.schedule.utils.TestHelper;

/*
 * Test cases for StoreVendorInfoController.class
 */
@RunWith(SpringRunner.class)
@WebMvcTest(StoreVendorInfoController.class)
public class StoreVendorInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	@MockBean
	private StoreVendorInfoService storeVendorInfoService;

	/**
	 * Defining constants
	 */
	private static final String VENDORS_URI = "/vendors";
	private static final String STORES_URI = "/stores";
	private static final String VENDOR_INFO_MAJOR_DEPT_URI = "/vendorinfo/Supplies";

	List<VendorRO> vendorROs = new ArrayList<>();
	List<StoreInfoRO> storeList = new ArrayList<>();
	List<MajorDepartmentRO> majorDepartmentROs=new ArrayList<>();
	List<MinorDepartmentRO> minorDepartmentROs=new ArrayList<>();
	List<DistrictRO> districtROs=new ArrayList<>();
	List<SourceRO> sourceROs=new ArrayList<>();

	/*
	 * initializing common objects required for test cases
	 */
	@Before
	public void setup() {
		this.mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		
		majorDepartmentROs.add(new MajorDepartmentRO("Supplies"));
		majorDepartmentROs.add(new MajorDepartmentRO("Bakery"));
		minorDepartmentROs.add(new MinorDepartmentRO("Meat Trays-Supplies"));
		minorDepartmentROs.add(new MinorDepartmentRO("Frozen-Ice Cream"));
		vendorROs.add(TestHelper.createVendorRO(1,"Natomas Meat Tray-Supplies - 80"));
		vendorROs.add(TestHelper.createVendorRO(2,"SSI Bakery -85"));
		vendorROs.add(new VendorRO("Natomas", 18));
		vendorROs.add(new VendorRO("SSI Lathrop", 19));
		storeList.add(TestHelper.createStoreRO(102));
		storeList.add(TestHelper.createStoreRO(103));
		districtROs.add(new DistrictRO("district1"));
		districtROs.add(new DistrictRO("district2"));
		sourceROs.add(new SourceRO("Natomas"));
	}

	/**
	 * Test case for retrieveVendors() without authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveVendorsTestWithoutAuth() throws Exception {
		when(storeVendorInfoService.retrieveVendors()).thenReturn(vendorROs);

		mockMvc.perform(get(VENDORS_URI).with(csrf())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveVendors() when vendor list is not empty with
	 * authentication when type=majorDepartments
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorsTestWithAuthWhenTypeNotNullAndTypeMajorDepartmentsAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveMajorDepartments()).thenReturn(majorDepartmentROs);

		mockMvc.perform(get(VENDORS_URI).param("type", "majorDepartments").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Major Department List fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendors() when major dept list is empty with
	 * authentication when type=majorDepartments
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorsTestWithAuthWhenTypeNotNullAndTypeMajorDepartmentsAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveMajorDepartments()).thenReturn(new ArrayList<MajorDepartmentRO>());

		mockMvc.perform(get(VENDORS_URI).param("type", "majorDepartments").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Major Departments list is empty"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendors() when vendor list is not empty with
	 * authentication when type=null
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorsTestWithAuthWhenTypeNullAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveVendors()).thenReturn(vendorROs);

		mockMvc.perform(get(VENDORS_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Vendor List fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendors() when major dept list is empty with
	 * authentication when type=null
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorsTestWithAuthWhenTypeNullAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveVendors()).thenReturn(new ArrayList<VendorRO>());

		mockMvc.perform(get(VENDORS_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Vendor list is empty"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

	
	/**
	 * Test case for retrieveStores() without authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveStoresTestWithoutAuth() throws Exception {
		when(storeVendorInfoService.retrieveStores()).thenReturn(storeList);

		mockMvc.perform(get(STORES_URI).with(csrf())).andExpect(status().isUnauthorized());
	}

	/**
	 * Test case for retrieveStores() when store list is not empty with
	 * authentication when district not null
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveStoresTestWithAuthWhenDistrictNotNullAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveStoresByDistrict(Mockito.anyString())).thenReturn(storeList);

		mockMvc.perform(get(STORES_URI).param("district", "district_name").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Store List fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveStores() when store list is empty with
	 * authentication when district not null
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveStoresTestWithAuthWhenDistrictNotNullAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveStoresByDistrict(Mockito.anyString())).thenReturn(new ArrayList<StoreInfoRO>());

		mockMvc.perform(get(STORES_URI).param("district", "district_name").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Store list is empty"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveStores() when store list is not empty with
	 * authentication when district null,type=districts
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveStoresTestWithAuthWhenDistrictNullAndTypeDistrictsAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveDistricts()).thenReturn(districtROs);

		mockMvc.perform(get(STORES_URI).param("type", "districts").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("District List fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveStores() when store list is empty with
	 * authentication when district null,type=districts
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveStoresTestWithAuthWhenDistrictNullAndTypeDistrictsAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveDistricts()).thenReturn(new ArrayList<DistrictRO>());

		mockMvc.perform(get(STORES_URI).param("type", "districts").with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("District list is empty"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveStores() when store list is not empty with
	 * authentication when district=null,type=null
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveStoresTestWithAuthWhenDistrictNullAndTypeNullAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveStores()).thenReturn(storeList);

		mockMvc.perform(get(STORES_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Store List fetched successfully."))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveStores() when store list is empty with
	 * authentication when district=null,type=null
	 * 
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveStoresTestWithAuthWhenDistrictNullAndTypeNullAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveStores()).thenReturn(new ArrayList<StoreInfoRO>());

		mockMvc.perform(get(STORES_URI).with(csrf().asHeader())).andExpect(status().isOk())
				.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
				.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Store list is empty"))
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendorInfo() when list is not empty
	 * with authentication when type=minorDepartments
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorInfoTestWithAuthWhenTypeNotNullAndTypeMinorDepartmentsAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveminorDepartment(Mockito.anyString())).thenReturn(minorDepartmentROs);
		
		mockMvc.perform(get(VENDOR_INFO_MAJOR_DEPT_URI).param("type", "minorDepartments").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
		.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Minor Department List fetched successfully."))
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendorInfo() when list is empty
	 * with authentication when type=minorDepartments
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorInfoTestWithAuthWhenTypeNotNullAndTypeMinorDepartmentsAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveminorDepartment(Mockito.anyString())).thenReturn(new ArrayList<MinorDepartmentRO>());
		
		mockMvc.perform(get(VENDOR_INFO_MAJOR_DEPT_URI).param("type", "minorDepartments").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
		.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Minor Department list is empty"))
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendorInfo() when list is not empty
	 * with authentication when type=source
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorInfoTestWithAuthWhenTypeNotNullAndTypeSourceAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveSource(Mockito.anyString())).thenReturn(sourceROs);
		
		mockMvc.perform(get(VENDOR_INFO_MAJOR_DEPT_URI).param("type", "source").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
		.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Source List fetched successfully."))
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendorInfo() when list is empty
	 * with authentication when type=source
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorInfoTestWithAuthWhenTypeNotNullAndTypeSourceAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveSource(Mockito.anyString())).thenReturn(new ArrayList<SourceRO>());
		
		mockMvc.perform(get(VENDOR_INFO_MAJOR_DEPT_URI).param("type", "source").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
		.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("Source list is empty"))
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendorInfo() when list is not empty
	 * with authentication when type=null
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorInfoTestWithAuthWhenTypeNullAndListNotEmpty() throws Exception {
		when(storeVendorInfoService.retrieveVendorName(Mockito.anyString())).thenReturn(vendorROs);
		
		mockMvc.perform(get(VENDOR_INFO_MAJOR_DEPT_URI).with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
		.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("vendor List fetched successfully."))
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}
	
	/**
	 * Test case for retrieveVendorInfo() when list is empty
	 * with authentication when type=null
	 * @throws Exception
	 */
	@Test
	@WithMockUser
	public void retrieveVendorInfoTestWithAuthWhenTypeNullAndListEmpty() throws Exception {
		when(storeVendorInfoService.retrieveVendorName(Mockito.anyString())).thenReturn(new ArrayList<VendorRO>());
		
		mockMvc.perform(get(VENDOR_INFO_MAJOR_DEPT_URI).with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(jsonPath(TestHelper.JSON_PATH_STATUS).value(HttpStatus.OK.value()))
		.andExpect(jsonPath(TestHelper.JSON_PATH_MESSAGE).value("vendor list is empty"))
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(APPLICATION_JSON));
	}

}
