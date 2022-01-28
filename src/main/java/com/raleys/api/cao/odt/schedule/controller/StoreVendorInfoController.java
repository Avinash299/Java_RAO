package com.raleys.api.cao.odt.schedule.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.representation.DistrictRO;
import com.raleys.api.cao.odt.schedule.representation.MajorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.MinorDepartmentRO;
import com.raleys.api.cao.odt.schedule.representation.SourceRO;
import com.raleys.api.cao.odt.schedule.representation.StoreInfoRO;
import com.raleys.api.cao.odt.schedule.representation.VendorRO;
import com.raleys.api.cao.odt.schedule.service.StoreVendorInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author swathi.kompella
 *
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class StoreVendorInfoController {

	Logger logger = LoggerFactory.getLogger(StoreVendorInfoController.class);

	@Autowired
	private StoreVendorInfoService storeVendorInfoService;

	@Operation(summary = SwaggerConstants.VENDOR_INFO_NOTES, description = SwaggerConstants.VENDOR_INFO_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendorRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@GetMapping("vendors")
	public Response retrieveVendors(@RequestParam(name = "type", required = false) String type) {
		if (type != null && type.equalsIgnoreCase("majorDepartments")) {
			List<MajorDepartmentRO> majorDepartments = storeVendorInfoService.retrieveMajorDepartments();
			if (majorDepartments.isEmpty()) {
				logger.info("No Major Department info found");
				return new Response(HttpStatus.OK.value(), "Major Departments list is empty", majorDepartments);
			} else {
				logger.info("Major Department info is fetched successfully");
				return new Response(HttpStatus.OK.value(), "Major Department List fetched successfully.",
						majorDepartments);
			}
		}

		else {
			List<VendorRO> vendors = storeVendorInfoService.retrieveVendors();
			if (vendors.isEmpty()) {
				logger.info("No vendor info found");
				return new Response(HttpStatus.OK.value(), "Vendor list is empty", vendors);
			} else {
				logger.info("Vendor info is fetched successfully");
				return new Response(HttpStatus.OK.value(), "Vendor List fetched successfully.", vendors);
			}
		}

	}

	@Operation(summary = SwaggerConstants.STORE_NOTES, description = SwaggerConstants.STORE_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StoreInfoRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@GetMapping("stores")
	public Response retrieveStores(@RequestParam(name = "district", required = false) String district,
			@RequestParam(name = "type", required = false) String type) {
		if (district != null) {
			List<StoreInfoRO> stores = storeVendorInfoService.retrieveStoresByDistrict(district);
			if (stores.isEmpty()) {
				logger.info("No store info found for given district");
				return new Response(HttpStatus.OK.value(), "Store list is empty", stores);
			} else {
				logger.info("store info is fetched successfully");
				return new Response(HttpStatus.OK.value(), "Store List fetched successfully.", stores);
			}
		} else if (type != null && type.equalsIgnoreCase("districts")) {
			List<DistrictRO> districts = storeVendorInfoService.retrieveDistricts();
			if (districts.isEmpty()) {
				logger.info("No districts found");
				return new Response(HttpStatus.OK.value(), "District list is empty", districts);
			} else {
				logger.info("Districts fetched successfully");
				return new Response(HttpStatus.OK.value(), "District List fetched successfully.", districts);
			}
		} else {
			List<StoreInfoRO> stores = storeVendorInfoService.retrieveStores();
			if (stores.isEmpty()) {
				logger.info("No store info found");
				return new Response(HttpStatus.OK.value(), "Store list is empty", stores);
			} else {
				logger.info("store info is fetched successfully");
				return new Response(HttpStatus.OK.value(), "Store List fetched successfully.", stores);
			}
		}

	}

	@Operation(summary = SwaggerConstants.VENDOR_INFO_NOTES, description = SwaggerConstants.VENDOR_INFO_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VendorRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@GetMapping("vendorinfo/{majorDept}")
	public Response retrieveVendorInfo(@PathVariable String majorDept,
			@RequestParam(name = "type", required = false) String type) {
		if (type != null && type.equalsIgnoreCase("minorDepartments")) {
			List<MinorDepartmentRO> minorDepartments = storeVendorInfoService.retrieveminorDepartment(majorDept);
			if (minorDepartments.isEmpty()) {
				logger.info("No minor department found");
				return new Response(HttpStatus.OK.value(), "Minor Department list is empty", minorDepartments);
			} else {
				logger.info("Minor Department is fetched successfully");
				return new Response(HttpStatus.OK.value(), "Minor Department List fetched successfully.",
						minorDepartments);
			}
		} else if (type != null && type.equalsIgnoreCase("source")) {
			List<SourceRO> source = storeVendorInfoService.retrieveSource(majorDept);
			if (source.isEmpty()) {
				logger.info("No source  found");
				return new Response(HttpStatus.OK.value(), "Source list is empty", source);
			} else {
				logger.info("Source is fetched successfully");
				return new Response(HttpStatus.OK.value(), "Source List fetched successfully.", source);
			}
		} else {
			List<VendorRO> vendor = storeVendorInfoService.retrieveVendorName(majorDept);
			if (vendor.isEmpty()) {
				logger.info("No vendor  found");
				return new Response(HttpStatus.OK.value(), "vendor list is empty", vendor);
			} else {
				logger.info("vendor is fetched successfully");
				return new Response(HttpStatus.OK.value(), "vendor List fetched successfully.", vendor);
			}
		}
	}

}
