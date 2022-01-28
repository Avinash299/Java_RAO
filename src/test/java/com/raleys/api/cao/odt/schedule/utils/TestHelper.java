package com.raleys.api.cao.odt.schedule.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.raleys.api.cao.odt.schedule.dto.CloneScheduleDTO;
import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.exception.ErrorMessage;
import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.Maintenance;
import com.raleys.api.cao.odt.schedule.model.Matrix;
import com.raleys.api.cao.odt.schedule.model.NamedSchedule;
import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.Schedule;
import com.raleys.api.cao.odt.schedule.model.StoreInfo;
import com.raleys.api.cao.odt.schedule.model.VendorInfo;
import com.raleys.api.cao.odt.schedule.model.WeeklySchedule;
import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;
import com.raleys.api.cao.odt.schedule.representation.MatrixScheduleRO;
import com.raleys.api.cao.odt.schedule.representation.NamedScheduleRO;
import com.raleys.api.cao.odt.schedule.representation.SearchScheduleRO;
import com.raleys.api.cao.odt.schedule.representation.StoreInfoRO;
import com.raleys.api.cao.odt.schedule.representation.VendorRO;

/**
 * Test helper class required for unit test cases
 *
 */
public class TestHelper {

	/**
	 * Defining constants required for test cases
	 */
	public static final String JSON_PATH_STATUS = "$.status";
	public static final String JSON_PATH_STATUS_CODE = "$.statusCode";
	public static final String JSON_PATH_MESSAGE = "$.message";

	/**
	 * Method to create delivery schedule model
	 * 
	 * @param dsId
	 * @param majorDept
	 * @param source
	 * @param minorDept
	 * @param store
	 * @param district
	 * @param orderDay
	 * @param orderCutoffTime
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param deliveryWindowEnd
	 * @param createDate
	 * @param modifiedDate
	 * @param modifiedBy
	 * @param effectiveDate
	 * @param vendorName
	 * @param supplier
	 * @param onShelfDay
	 * @param onShelfHours
	 * @param createLeadTime
	 * @param scheduleType
	 * @param deliveryHour
	 * @param onShelfTime
	 * @return WeeklySchedule
	 */
	public static WeeklySchedule createWeeklySchedule(Integer dsId, Integer storeId, String orderDay,
			String orderCutoffTime, String deliveryDay, String deliveryWindowBegin, String deliveryWindowEnd,
			Date createDate, Date modifiedDate, String modifiedBy, Date effectiveDate, Integer vendorId,
			String onShelfDay, Integer onShelfHours, Integer createLeadTime, String scheduleType, Integer deliveryHour,
			Integer onShelfTime, Integer holidayId, int deleteFlag, String createdBy, Date endDate, String status,
			int applyFlag) {

		WeeklySchedule ds = new WeeklySchedule();
		ds.setScheduleCreateDate(createDate);
		ds.setScheduleCreateLeadTime(createLeadTime);
		ds.setScheduleDeliveryDay(deliveryDay);
		ds.setScheduleDeliveryHour(deliveryHour);
		ds.setDeliveryBegin(deliveryWindowBegin);
		ds.setDeliveryEnd(deliveryWindowEnd);
		ds.setDsId(dsId);
		ds.setScheduleEffectiveDate(effectiveDate);
		ds.setScheduleModifiedBy(modifiedBy);
		ds.setScheduleModifiedDate(modifiedDate);
		ds.setScheduleOnShelfDay(onShelfDay);
		ds.setScheduleOnShelfHours(onShelfHours);
		ds.setScheduleOrderCutoffTime(orderCutoffTime);
		ds.setScheduleOrderDay(orderDay);
		ds.setScheduleType(scheduleType);
		ds.setStoreId(storeId);
		ds.setVendorId(vendorId);
		ds.setNamedScheduleId(holidayId);
		ds.setScheduleDeleteFlag(deleteFlag);
		ds.setScheduleCreatedBy(createdBy);
		ds.setScheduleEndDate(endDate);
		ds.setScheduleStatus(status);
		ds.setDsApply(applyFlag);

		return ds;
	}

	/**
	 * Method to create current schedule RO
	 * 
	 * @param orderDay
	 * @param orderCutoffTime
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param deliveryWindowEnd
	 * @param effectiveDate
	 * @param onShelfDay
	 * @param onShelfHours
	 * @param createLeadTime
	 * @param deliveryHour
	 * @param onShelfTime
	 * @return CurrentScheduleRO
	 */
	public static CurrentScheduleRO createCurrentScheduleRO(String orderDay, String orderCutoffTime, String deliveryDay,
			String deliveryWindowBegin, String deliveryWindowEnd, String effectiveDate, String onShelfDay,
			Integer onShelfHours, Integer createLeadTime, Integer deliveryHour, Integer dsId, int deleteFlag) {
		CurrentScheduleRO dto = new CurrentScheduleRO();
		dto.setOrderDay(orderDay);
		dto.setOrderCutoffTime(orderCutoffTime);
		dto.setDeliveryDay(deliveryDay);
		dto.setDeliveryWindowBegin(deliveryWindowBegin);
		dto.setDeliveryWindowEnd(deliveryWindowEnd);
		dto.setDeliveryHour(deliveryHour);
		dto.setOnShelfDay(onShelfDay);
		dto.setOnShelfHours(onShelfHours);
		dto.setCreateLeadTime(createLeadTime);
		dto.setEffectiveDate(effectiveDate);
		dto.setDsId(dsId);
		dto.setDeleteFlag(deleteFlag);

		return dto;
	}

	/**
	 * Method to create store RO object
	 * 
	 * @param storeId
	 * @return StoreRO
	 */
	public static StoreInfoRO createStoreRO(int storeId) {
		StoreInfoRO storeRO = new StoreInfoRO();
		storeRO.setStoreId(storeId);
		return storeRO;
	}

	/**
	 * Method to create Store info attributes
	 * 
	 * @param storeId
	 * @param district
	 * @param storeNumber
	 * @param storeFaxNumber
	 * @param streetAddress
	 * @param opx
	 * @return StoreInfo
	 */
	public static StoreInfo createStoreInfo(int storeId, String district) {

		StoreInfo storeInfo = new StoreInfo();
		storeInfo.setDistrict(district);
		storeInfo.setStore(storeId);

		return storeInfo;
	}

	/**
	 * Method to create vendor ro object
	 * 
	 * @param vendorName
	 * @param vendorNumber
	 * @param source
	 * @param majorDept
	 * @param minorDept
	 * @return VendorRO
	 */
	public static VendorRO createVendorRO(int vendorId, String vendor) {
		VendorRO vendorRO = new VendorRO();
		vendorRO.setVendorId(vendorId);
		vendorRO.setVendor(vendor);
		return vendorRO;
	}

	/**
	 * Method to create vendor info
	 * 
	 * @param itascaComment
	 * @param supplier
	 * @param vendorSource
	 * @param majorDept
	 * @param minorDept
	 * @return VendorInfo
	 */
	public static VendorInfo createVendorInfo(int vendorId, String itascaComment, int supplier, String vendorSource,
			String majorDept, String minorDept) {
		VendorInfo vendorInfo = new VendorInfo();
		vendorInfo.setVendorId(vendorId);
		vendorInfo.setItascaComment(itascaComment);
		vendorInfo.setSupplier(supplier);
		vendorInfo.setVendorSource(vendorSource);
		vendorInfo.setMajorDept(majorDept);
		vendorInfo.setMinorDept(minorDept);

		return vendorInfo;
	}

	public static SearchScheduleRO createSearchScheduleRO(String orderDay, String orderCutoffTime, String deliveryDay,
			String deliveryWindowBegin, String deliveryWindowEnd, String effectiveDate, String endDate, String district,
			int store, String vendor, String majorDept, Integer dsId) {
		SearchScheduleRO searchScheduleRO = new SearchScheduleRO();
		searchScheduleRO.setOrderDay(orderDay);
		searchScheduleRO.setOrderCutoffTime(orderCutoffTime);
		searchScheduleRO.setDeliveryDay(deliveryDay);
		searchScheduleRO.setDeliveryWindowBegin(deliveryWindowBegin);
		searchScheduleRO.setDeliveryWindowEnd(deliveryWindowEnd);
		searchScheduleRO.setEffectiveDate(effectiveDate);
		searchScheduleRO.setEndDate(endDate);
		searchScheduleRO.setDistrict(district);
		searchScheduleRO.setStore(store);
		searchScheduleRO.setVendor(vendor);
		searchScheduleRO.setMajorDept(majorDept);
		searchScheduleRO.setDsId(dsId);

		return searchScheduleRO;
	}

	/**
	 * Method to create new delivery schedule
	 * 
	 * @param dsId
	 * @param orderDay
	 * @param orderCutoffTime
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param deliveryWindowEnd
	 * @param effectiveDate
	 * @param onShelfDay
	 * @param onShelfHours
	 * @param createLeadTime
	 * @param deliveryHour
	 * @param deleteFlag
	 * @return DeliverySchedule
	 */
	public static DeliverySchedule createNewSchedule(Integer dsId, String orderDay, String orderCutoffTime,
			String deliveryDay, String deliveryWindowBegin, String deliveryWindowEnd, String effectiveDate,
			String onShelfDay, Integer onShelfHours, Integer createLeadTime, Integer deliveryHour, int deleteFlag,
			String status) {
		DeliverySchedule newSchedule = new DeliverySchedule();
		newSchedule.setOrderDay(orderDay);
		newSchedule.setOrderCutoffTime(orderCutoffTime);
		newSchedule.setDeliveryDay(deliveryDay);
		newSchedule.setDeliveryWindowBegin(deliveryWindowBegin);
		newSchedule.setDeliveryWindowEnd(deliveryWindowEnd);
		newSchedule.setDeliveryHour(deliveryHour);
		newSchedule.setOnShelfDay(onShelfDay);
		newSchedule.setOnShelfHours(onShelfHours);
		newSchedule.setCreateLeadTime(createLeadTime);
		newSchedule.setEffectiveDate(effectiveDate);
		newSchedule.setDsId(dsId);
		newSchedule.setDeleteFlag(deleteFlag);
		newSchedule.setStatus(status);
		return newSchedule;
	}

	/**
	 * Method to create NamedScheduleRO
	 * 
	 * @param holidayId
	 * @param holiday
	 * @param year
	 * @return HolidayRO
	 */
	public static NamedScheduleRO createHolidayRO(int holidayId, String holiday, String effectiveDate, String endDate,
			String status) {
		NamedScheduleRO holidayRO = new NamedScheduleRO();
		holidayRO.setHolidayId(holidayId);
		holidayRO.setNamedSchedule(holiday);
		holidayRO.setEffectiveDate(effectiveDate);
		holidayRO.setEndDate(endDate);
		holidayRO.setStatus(status);

		return holidayRO;
	}

	/**
	 * Method to create HolidayInfo
	 * 
	 * @param id
	 * @param name
	 * @return NamedSchedule
	 */
	public static NamedSchedule createHolidayInfo(int id, String name) {
		NamedSchedule holidayInfo = new NamedSchedule();
		holidayInfo.setId(id);
		holidayInfo.setName(name);

		return holidayInfo;
	}

	/**
	 * Method to create NamedSchedule
	 * 
	 * @param id
	 * @param name
	 * @param effectiveDate
	 * @param endDate
	 * @param status
	 * @param createdDate
	 * @param modifiedDate
	 * @param modifiedBy
	 * @param createdBy
	 * @return NamedSchedule
	 */
	public static NamedSchedule createNamedSchedule(int id, String name, Date effectiveDate, Date endDate,
			String status, Date createdDate, Date modifiedDate, String modifiedBy, String createdBy) {
		NamedSchedule namedSchedule = new NamedSchedule();
		namedSchedule.setId(id);
		namedSchedule.setName(name);
		namedSchedule.setEffectiveDate(effectiveDate);
		namedSchedule.setEndDate(endDate);
		namedSchedule.setStatus(status);
		namedSchedule.setCreatedDate(createdDate);
		namedSchedule.setModifiedDate(modifiedDate);
		namedSchedule.setModifiedBy(modifiedBy);
		namedSchedule.setCreatedBy(createdBy);

		return namedSchedule;
	}

	/**
	 * Method to clone NamedSchedule
	 * 
	 * @param name
	 * @param createdBy
	 * @return
	 */
	public static CloneScheduleDTO cloneNamedSchedule(String name, String status, int id) {
		CloneScheduleDTO namedSchedule = new CloneScheduleDTO();
		namedSchedule.setNamedSchedule(name);
		namedSchedule.setHolidayId(id);
		namedSchedule.setStatus(status);

		return namedSchedule;
	}

	/**
	 * Method to create Maintenance
	 * 
	 * @param name
	 * @param effectiveDate
	 * @param endDate
	 * @return Maintenance
	 */
	public static Maintenance createMaintenance(String name, String effectiveDate, String endDate) {
		Maintenance maintenance = new Maintenance();
		maintenance.setNamedSchedule(name);
		maintenance.setEffectiveDate(effectiveDate);
		maintenance.setEndDate(endDate);

		return maintenance;
	}

	/**
	 * Method to create FutureScheduleRO
	 * 
	 * @param orderDay
	 * @param orderCutoffTime
	 * @param deliveryDay
	 * @param deliveryWindowBegin
	 * @param deliveryWindowEnd
	 * @param endDate
	 * @param onShelfDay
	 * @param onShelfHours
	 * @param createLeadTime
	 * @param deliveryHour
	 * @param dsId
	 * @param holidayId
	 * @return FutureScheduleRO
	 */
	public static FutureScheduleRO createFutureScheduleRO(String orderDay, String orderCutoffTime, String deliveryDay,
			String deliveryWindowBegin, String deliveryWindowEnd, String endDate, String onShelfDay,
			Integer onShelfHours, Integer createLeadTime, Integer deliveryHour, Integer dsId, Integer holidayId) {
		FutureScheduleRO futureScheduleRO = new FutureScheduleRO();
		futureScheduleRO.setOrderDay(orderDay);
		futureScheduleRO.setOrderCutoffTime(orderCutoffTime);
		futureScheduleRO.setDeliveryDay(deliveryDay);
		futureScheduleRO.setDeliveryWindowBegin(deliveryWindowBegin);
		futureScheduleRO.setDeliveryWindowEnd(deliveryWindowEnd);
		futureScheduleRO.setOnShelfDay(onShelfDay);
		futureScheduleRO.setOnShelfHours(onShelfHours);
		futureScheduleRO.setCreateLeadTime(createLeadTime);
		futureScheduleRO.setDeliveryHour(deliveryHour);
		futureScheduleRO.setDsId(dsId);
		futureScheduleRO.setHolidayId(holidayId);

		return futureScheduleRO;
	}

	/**
	 * Method to create default Oidc user
	 * 
	 * @param name
	 * @param email
	 * @return OAuth2User
	 */
	public static OAuth2User createOidcUser(String name, String email) {
		SimpleGrantedAuthority simpleGrantedAuthorityAdmin = new SimpleGrantedAuthority("ADMIN");
		SimpleGrantedAuthority simpleGrantedAuthorityUser = new SimpleGrantedAuthority("USER");

		List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
		simpleGrantedAuthorityList.add(simpleGrantedAuthorityAdmin);
		simpleGrantedAuthorityList.add(simpleGrantedAuthorityUser);

		final String ID_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"
				+ ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsIm"
				+ "p0aSI6ImQzNWRmMTRkLTA5ZjYtNDhmZi04YTkzLTdjNmYwMzM5MzE1OSIsImlhdCI6MTU0M"
				+ "Tk3MTU4MywiZXhwIjoxNTQxOTc1MTgzfQ.QaQOarmV8xEUYV7yvWzX3cUE_4W1luMcWCwpr" + "oqqUrg";

		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", "1234567890");
		claims.put("name", name);
		claims.put("email", email);
		claims.put("extension_Role", "ADMIN");

		return new DefaultOidcUser(simpleGrantedAuthorityList,
				new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims),
				new OidcUserInfo(claims));
	}

	/**
	 * Method to return authentication token
	 * 
	 * @param principal
	 * @return Authentications
	 */
	public static Authentication getOAuthAuthenticationFor(OAuth2User principal) {
		Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
		String authorizedClientRegistrationId = "my-oauth-client";

		return new OAuth2AuthenticationToken(principal, authorities, authorizedClientRegistrationId);
	}

	/**
	 * Set error status code and message
	 * 
	 * @param statusCode
	 * @param message
	 * @return
	 */
	public static ErrorMessage setErrorMessage(int statusCode, String message) {
		ErrorMessage error = new ErrorMessage();
		error.setStatusCode(statusCode);
		error.setMessage(message);
		return error;

	}

	/**
	 * @return
	 */
	public static DeliveryScheduleDTO setNewScheduleDTO() {
		DeliveryScheduleDTO dto = new DeliveryScheduleDTO();
		dto.setDsId(102);
		dto.setOrderDay("Sunday");
		return dto;

	}

	/**
	 * @param statusCode
	 * @param message
	 * @param result
	 * @return
	 */
	public static Response setResponse(int statusCode, String message, Object result) {
		Response response = new Response();
		response.setStatus(statusCode);
		response.setMessage(message);
		response.setResult(result);
		return response;

	}

	/**
	 * @param searchDsId
	 * @param store
	 * @param searchOrderDay
	 * @param searchOrderCutoffTime
	 * @param searchDeliveryDay
	 * @param searchDeliveryBegin
	 * @param searchDeliveryEnd
	 * @param searchEffectiveDate
	 * @param searchEndDate
	 * @param searchVendorId
	 * @param namedScheduleId
	 * @param searchVendorName
	 * @param searchSupplier
	 * @param searchDistrict
	 * @return Schedule
	 */
	public static Schedule createSchedule(Integer searchDsId, int store, String searchOrderDay,
			String searchOrderCutoffTime, String searchDeliveryDay, String searchDeliveryBegin,
			String searchDeliveryEnd, Date searchEffectiveDate, Date searchEndDate, int searchVendorId,
			int namedScheduleId, String searchVendorName, int searchSupplier, String searchDistrict,
			String searchMajorDept, String searchSource, String searchMinorDept) {
		Schedule schedule = new Schedule();
		schedule.setSearchDsId(searchDsId);
		schedule.setStore(store);
		schedule.setSearchOrderDay(searchOrderDay);
		schedule.setSearchOrderCutoffTime(searchOrderCutoffTime);
		schedule.setSearchDeliveryDay(searchDeliveryDay);
		schedule.setSearchDeliveryBegin(searchDeliveryBegin);
		schedule.setSearchDeliveryEnd(searchDeliveryEnd);
		schedule.setSearchEffectiveDate(searchEffectiveDate);
		schedule.setSearchEndDate(searchEndDate);
		schedule.setSearchVendorId(searchVendorId);
		schedule.setNamedScheduleId(namedScheduleId);
		schedule.setSearchVendorName(searchVendorName);
		schedule.setSearchSupplier(searchSupplier);
		schedule.setSearchDistrict(searchDistrict);
		schedule.setSearchMajorDept(searchMajorDept);
		schedule.setSearchSource(searchSource);
		schedule.setSearchMinorDept(searchMinorDept);

		return schedule;
	}

	public static Matrix createMatrix(Integer searchDsId, String searchOrderDay, String searchOrderCutoffTime,
			String searchDeliveryBegin, String searchDeliveryEnd, String searchVendorName, String searchMajorDept) {
		Matrix matrix = new Matrix();
		matrix.setSearchDsId(searchDsId);
		matrix.setSearchOrderDay(searchOrderDay);
		matrix.setSearchOrderCutoffTime(searchOrderCutoffTime);
		matrix.setSearchDeliveryBegin(searchDeliveryBegin);
		matrix.setSearchDeliveryEnd(searchDeliveryEnd);
		matrix.setSearchVendorName(searchVendorName);
		matrix.setSearchMajorDept(searchMajorDept);
		return matrix;
	}

	/**
	 * @param source
	 * @param minorDept
	 * @param vendor
	 * @param orderDay
	 * @param orderCutoffTime
	 * @param deliveryDay
	 * @param windowBegin
	 * @param windowEnd
	 * @param effectiveDate
	 * @param endDate
	 * @return AdvancedFilters
	 */
	public static AdvancedFilters createAdvancedFilters(String source, String minorDept, int vendor, String orderDay,
			String orderCutoffTime, String deliveryDay, String windowBegin, String windowEnd, String effectiveDate,
			String endDate) {
		AdvancedFilters advancedFilters = new AdvancedFilters();
		advancedFilters.setSource(source);
		advancedFilters.setMinorDept(minorDept);
		advancedFilters.setVendorId(vendor);
		advancedFilters.setOrderDay(orderDay);
		advancedFilters.setOrderCutoffTime(orderCutoffTime);
		advancedFilters.setDeliveryDay(deliveryDay);
		advancedFilters.setWindowBegin(windowBegin);
		advancedFilters.setWindowEnd(windowEnd);
		advancedFilters.setEffectiveDateFrom(effectiveDate);
		advancedFilters.setEffectiveDateTo(endDate);

		return advancedFilters;
	}

	public static MatrixScheduleRO createMatrixRO(String orderDay, String orderCutoffTime, String delivery,
			String department, String vendor) {
		MatrixScheduleRO matrixScheduleRO = new MatrixScheduleRO();
		matrixScheduleRO.setOrderDay(orderDay);
		matrixScheduleRO.setOrderCutoffTime(orderCutoffTime);
		matrixScheduleRO.setDelivery(delivery);
		matrixScheduleRO.setDepartment(department);
		matrixScheduleRO.setVendor(vendor);
		return matrixScheduleRO;
	}
}
