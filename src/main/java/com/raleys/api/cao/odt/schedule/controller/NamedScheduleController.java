package com.raleys.api.cao.odt.schedule.controller;

import static com.raleys.api.cao.odt.schedule.model.Constants.EFFECTIVEDATEFORMAT;
import static com.raleys.api.cao.odt.schedule.model.Constants.USERNAME;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.BAD_REQUEST;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_ACTIVATED;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_CLONE;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_DELETE;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_EMPTY;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_EXIST;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_LIST;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_PUBLISHED;
import static com.raleys.api.cao.odt.schedule.model.ResponseMessage.NAMEDSCHEDULE_UPDATE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.dto.CloneScheduleDTO;
import com.raleys.api.cao.odt.schedule.model.Constants.NAMEDSCHEDULEACTION;
import com.raleys.api.cao.odt.schedule.model.Maintenance;
import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.representation.NamedScheduleRO;
import com.raleys.api.cao.odt.schedule.service.NamedScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author abhay.thakur
 *
 */
@RestController
@RequestMapping("/namedSchedule")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class NamedScheduleController {

	Logger logger = LoggerFactory.getLogger(NamedScheduleController.class);
	DateFormat simpleDateFormat = new SimpleDateFormat(EFFECTIVEDATEFORMAT);

	@Autowired
	NamedScheduleService namedScheduleService;
	@Autowired
	HttpServletRequest httpServletRequest;

	@GetMapping("/list")
	@Operation(summary = SwaggerConstants.RETRIEVE_NAMED_SCHEDULE_NOTES, description = SwaggerConstants.RETRIEVE_NAMED_SCHEDULE_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = NamedScheduleRO.class)))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	public Response retrieveNamedSchedule(@RequestParam(name = "maintenance", required = false) boolean maintenance) {
		List<NamedScheduleRO> namedScheduleROs = namedScheduleService.retrieveNamedSchedule(maintenance);
		if (namedScheduleROs.isEmpty()) {
			logger.info(NAMEDSCHEDULE_EMPTY);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_EMPTY, namedScheduleROs);
		} else {
			logger.info(NAMEDSCHEDULE_LIST);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_LIST, namedScheduleROs);
		}

	}

	@Operation(summary = SwaggerConstants.CLONE_NAMED_SCHEDULE_NOTES, description = SwaggerConstants.CLONE_NAMED_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string")) }, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CloneScheduleDTO.class))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PostMapping("/clone")
	public Response cloneNamedSchedule(@RequestParam @NotNull @NotBlank String name) {
		CloneScheduleDTO cloneScheduleDTO = namedScheduleService.cloneNamedSchedule(name,
				httpServletRequest.getHeader(USERNAME));
		if (cloneScheduleDTO != null) {
			logger.info(NAMEDSCHEDULE_CLONE);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_CLONE, cloneScheduleDTO);
		} else {
			logger.error(NAMEDSCHEDULE_EXIST);
			return new Response(HttpStatus.BAD_REQUEST.value(), NAMEDSCHEDULE_EXIST, cloneScheduleDTO);

		}
	}

	@Operation(summary = SwaggerConstants.DELETE_NAMED_SCHEDULE_NOTES, description = SwaggerConstants.DELETE_NAMED_SCHEDULE_VALUE, responses = {
			@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
			@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@DeleteMapping("/delete/{holidayId}")
	public Response deleteNamedSchedule(@PathVariable @NotBlank int holidayId) {
		if (namedScheduleService.deleteNamedSchedule(holidayId) > 0) {
			logger.info(NAMEDSCHEDULE_DELETE);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_DELETE, holidayId);
		} else {
			logger.error(BAD_REQUEST);
			return new Response(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST, holidayId);
		}

	}

	@Operation(summary = SwaggerConstants.UPDATE_NAMED_SCHEDULE_NOTES, description = SwaggerConstants.UPDATE_NAMED_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string")) }, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Maintenance.class))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PutMapping("/update")
	public Response updateNamedSchedule(@RequestBody Maintenance maintenance) {
		String msg = namedScheduleService.updateNamedSchedule(httpServletRequest.getHeader(USERNAME),
				NAMEDSCHEDULEACTION.UPDATE.toString(), maintenance);
		if (msg.equals(NAMEDSCHEDULEACTION.UPDATE.toString())) {
			logger.info(NAMEDSCHEDULE_UPDATE);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_UPDATE, maintenance);
		} else {
			logger.error(msg);
			return new Response(HttpStatus.BAD_REQUEST.value(), msg, maintenance);
		}
	}

	@Operation(summary = SwaggerConstants.PUBLISH_NAMED_SCHEDULE_NOTES, description = SwaggerConstants.PUBLISH_NAMED_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string")) }, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Maintenance.class))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PutMapping("/publish")
	public Response publishNamedSchedule(@RequestBody Maintenance maintenance) {
		String msg = namedScheduleService.updateNamedSchedule(httpServletRequest.getHeader(USERNAME),
				NAMEDSCHEDULEACTION.PUBLISH.toString(), maintenance);
		if (msg.equals(NAMEDSCHEDULEACTION.PUBLISH.toString())) {
			logger.info(NAMEDSCHEDULE_PUBLISHED);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_PUBLISHED, maintenance);
		} else {
			logger.error(msg);
			return new Response(HttpStatus.BAD_REQUEST.value(), msg, maintenance);
		}
	}

	@Operation(summary = SwaggerConstants.ACTIVATE_NAMED_SCHEDULE_NOTES, description = SwaggerConstants.ACTIVATE_NAMED_SCHEDULE_VALUE, parameters = {
			@Parameter(in = ParameterIn.HEADER, name = USERNAME, required = true,schema = @Schema(type = "string")) }, responses = {
					@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Maintenance.class))),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
					@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR) })
	@PutMapping("/active")
	public Response activateNamedSchedule(@RequestBody Maintenance maintenance) {
		String msg = namedScheduleService.updateNamedSchedule(httpServletRequest.getHeader(USERNAME),
				NAMEDSCHEDULEACTION.ACTIVE.toString(), maintenance);
		if (msg.equals(NAMEDSCHEDULEACTION.ACTIVE.toString())) {
			logger.info(NAMEDSCHEDULE_ACTIVATED);
			return new Response(HttpStatus.OK.value(), NAMEDSCHEDULE_ACTIVATED, maintenance);
		} else {
			logger.error(msg);
			return new Response(HttpStatus.BAD_REQUEST.value(), msg, maintenance);
		}
	}
}
