package com.raleys.api.cao.odt.schedule.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.representation.MatrixScheduleRO;
import com.raleys.api.cao.odt.schedule.service.MatrixScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author abhay.thakur Controller class for schedule matrix api.
 *
 *
 */
@RestController
@RequestMapping("/matrix")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class MatrixScheduleController {
	Logger logger = LoggerFactory.getLogger(MatrixScheduleController.class);

	@Autowired
	private MatrixScheduleService matrixScheduleService;

	@Operation(summary = SwaggerConstants.MASTER_SCHEDULE_MATRIX_NOTES, description = SwaggerConstants.MASTER_SCHEDULE_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MatrixScheduleRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@GetMapping("/master/{store}")
	public Response retrieveMasterScheduleMatrix(@PathVariable int store,
			@RequestParam(name = "district", required = false) String district) {
		List<MatrixScheduleRO> matrixROs = matrixScheduleService.retrieveMasterSchedule(district, store);
		if (matrixROs.isEmpty()) {
			logger.info("Master Schedule Matrix list not found");
			return new Response(HttpStatus.OK.value(), "Master Schedule Matrix list is empty.", matrixROs);
		} else {
			logger.info("Master Schedule Matrix list is fetched successfully");
			return new Response(HttpStatus.OK.value(), "Master Schedule Matrix fetched successfully.", matrixROs);
		}

	}

	@Operation(summary = SwaggerConstants.HOLIDAY_SCHEDULE_MATRIX_NOTES, description = SwaggerConstants.HOLIDAY_SCHEDULE_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MatrixScheduleRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })

	@GetMapping("/holiday/{store}/{holidayId}")
	public Response retrieveHolidayScheduleMatrix(@PathVariable int store, @PathVariable int holidayId,
			@RequestParam(name = "district", required = false) String district) {
		List<MatrixScheduleRO> matrixROs = matrixScheduleService.retrieveHolidaySchedule(holidayId, district, store);
		if (matrixROs.isEmpty()) {
			logger.info("Holiday Schedule Matrix list not found");
			return new Response(HttpStatus.OK.value(), "Holiday Schedule Matrix list is empty.", matrixROs);
		} else {
			logger.info("Holiday Schedule Matrix list is fetched successfully");
			return new Response(HttpStatus.OK.value(), "Holiday Schedule Matrix fetched successfully.", matrixROs);
		}

	}
}
