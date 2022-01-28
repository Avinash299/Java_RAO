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
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.representation.CurrentScheduleRO;
import com.raleys.api.cao.odt.schedule.service.CurrentWeekScheduleService;
import com.raleys.api.cao.odt.schedule.util.CustomValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


/**
 * @author abhay.thakur Controller class for current schedule api.
 *
 *
 */
@RestController
@RequestMapping("/currentSchedule")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class CurrentWeekScheduleController {
	Logger logger = LoggerFactory.getLogger(CurrentWeekScheduleController.class);

	@Autowired
	private CurrentWeekScheduleService currentWeekScheduleService;

	 @Operation(summary = SwaggerConstants.CURRENT_SCHEDULE_NOTES,description = SwaggerConstants.CURRENT_SCHEDULE_VALUE,
	            responses = {
	 @ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json",array =@ArraySchema(schema = @Schema(implementation = CurrentScheduleRO.class)))),
		@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED) ,
		@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
		@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR)})
	 
	@GetMapping("/list/{storeId}/{vendorId}")
	public Response retrieveCurrentSchedule(@PathVariable int storeId, @PathVariable int vendorId) {
		CustomValidator validator = new CustomValidator();
		validator.validateStoreandVendor(storeId, vendorId);
		List<CurrentScheduleRO> dtos = currentWeekScheduleService.retrieveCurrentSchedule(storeId, vendorId);
		if (dtos.isEmpty()) {
			logger.info("CurrentSchedule list not found");
			return new Response(HttpStatus.OK.value(), "CurrentSchedule list is empty.", dtos);
		} else {
			logger.info("CurrentSchedule list is fetched successfully");
			return new Response(HttpStatus.OK.value(), "CurrentSchedule fetched successfully.", dtos);
		}

	}
}
