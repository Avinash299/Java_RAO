package com.raleys.api.cao.odt.schedule.controller;

import static com.raleys.api.cao.odt.schedule.model.Constants.USERNAME;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.model.DeliverySchedule;
import com.raleys.api.cao.odt.schedule.model.ListValidationWrapper;
import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.service.NewScheduleService;
import com.raleys.api.cao.odt.schedule.util.CustomValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author abhay.thakur Controller class for save new schedule api.
 */

@RestController
@RequestMapping("/newSchedule")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class NewScheduleController {
	Logger logger = LoggerFactory.getLogger(NewScheduleController.class);

	@Autowired
	private NewScheduleService newScheduleService;
	@Autowired
	HttpServletRequest httpServletRequest;

	@Operation(summary = SwaggerConstants.SAVE_NEW_SCHEDULE_NOTES, description = SwaggerConstants.SAVE_NEW_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string"))}, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DeliverySchedule.class)))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PostMapping("/save/{storeId}/{vendorId}")
	public Response saveNewSchedule(@PathVariable int storeId, @PathVariable int vendorId,
			@Valid @RequestBody ListValidationWrapper<DeliverySchedule> newSchedules, BindingResult bindingResult) {
		CustomValidator validator = new CustomValidator();
		validator.validateStoreandVendor(storeId, vendorId);
		validator.validateNewScheduleRequest(bindingResult);
		logger.info("New Schedule saved successfully.");
		return new Response(HttpStatus.OK.value(), "New Schedules saved/updated successfully.", newScheduleService
				.saveAllNewSchedule(newSchedules.getList(), storeId, vendorId, httpServletRequest.getHeader(USERNAME)));
	}

}
