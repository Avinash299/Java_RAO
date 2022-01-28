package com.raleys.api.cao.odt.schedule.controller;

import static com.raleys.api.cao.odt.schedule.model.Constants.USERNAME;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.dto.DeliveryScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.representation.FutureScheduleRO;
import com.raleys.api.cao.odt.schedule.service.FutureScheduleService;
import com.raleys.api.cao.odt.schedule.util.CustomValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/futureSchedule")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class FutureScheduleController {

	Logger logger = LoggerFactory.getLogger(FutureScheduleController.class);

	@Autowired
	FutureScheduleService futureScheduleService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Operation(summary = SwaggerConstants.RETRIEVE_FUTURE_SCHEDULE_NOTES, description = SwaggerConstants.RETRIEVE_FUTURE_SCHEDULE_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FutureScheduleRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@GetMapping("/list/{storeId}/{vendorId}/{holidayId}")
	public Response retrieveFutureSchedule(@PathVariable int storeId, @PathVariable int vendorId,
			@PathVariable @NotBlank int holidayId) {

		CustomValidator validator = new CustomValidator();
		validator.validateStoreandVendor(storeId, vendorId);
		validator.validateHoliday(holidayId);

		List<FutureScheduleRO> futureScheduleROs = futureScheduleService.retrieveFutureSchedule(storeId, vendorId,
				holidayId);
		if (futureScheduleROs.isEmpty()) {
			logger.info("FutureSchedule list not found");
			return new Response(HttpStatus.OK.value(), "FutureSchedule list is empty.", futureScheduleROs);
		} else {
			logger.info("FutureSchedule list is fetched successfully");
			return new Response(HttpStatus.OK.value(), "FutureSchedule fetched successfully.", futureScheduleROs);
		}

	}

	@Operation(summary = SwaggerConstants.SAVE_FUTURE_SCHEDULE_NOTES, description = SwaggerConstants.SAVE_FUTURE_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string"))}, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeliveryScheduleDTO.class))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PostMapping("/save/{storeId}/{vendorId}/{holidayId}")
	public Response saveFutureSchedule(@PathVariable int storeId, @PathVariable int vendorId,
			@PathVariable int holidayId, @Valid @RequestBody DeliverySchedule deliverySchedule, BindingResult result) {
		CustomValidator validator = new CustomValidator();
		validator.validateStoreandVendor(storeId, vendorId);
		validator.validateHoliday(holidayId);
		validator.validateNewScheduleRequest(result);
		DeliveryScheduleDTO deliveryScheduleDTO = futureScheduleService.saveFutureSchedule(deliverySchedule, storeId,
				vendorId, holidayId, httpServletRequest.getHeader(USERNAME));
		if (deliveryScheduleDTO != null) {
			logger.info("Future Schedule saved successfully.");
			return new Response(HttpStatus.OK.value(), "Future Schedule saved/updated successfully.",
					deliveryScheduleDTO);
		} else {
			logger.info("Holiday Schedule already active.");
			return new Response(HttpStatus.BAD_REQUEST.value(),
					"Holiday Schedule already active, Please check the maintenance screen.", deliveryScheduleDTO);
		}

	}

	@Operation(summary = SwaggerConstants.REMOVE_FUTURE_SCHEDULE_NOTES, description = SwaggerConstants.REMOVE_FUTURE_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string")) }, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS_PUT, description = SwaggerConstants.SUCCESS_NOCONTENT, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PutMapping("/remove/{dsId}")
	public Response removeFutureSchedule(@PathVariable int dsId) {

		int updated = futureScheduleService.removeScheduleDay(httpServletRequest.getHeader(USERNAME), dsId);

		if (updated > 0) {
			logger.info("Future Schedule logically removed  Sucessfully for delivery schedule id {} ", dsId);
			return new Response(HttpStatus.NO_CONTENT.value(), "Future Schedule logically removed  Sucessfully", dsId);
		} else {
			logger.info("Future Schedule is not updated for delivery schedule id {} ", dsId);
			return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Future Schedule is not updated ", dsId);
		}

	}

}
