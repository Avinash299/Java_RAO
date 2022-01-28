package com.raleys.api.cao.odt.schedule.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raleys.api.cao.odt.schedule.model.AdvancedFilters;
import com.raleys.api.cao.odt.schedule.model.Response;
import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;
import com.raleys.api.cao.odt.schedule.representation.SearchScheduleRO;
import com.raleys.api.cao.odt.schedule.service.SearchDeliveryScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/searchSchedule")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = SwaggerConstants.AUTHORIZATION)
public class SearchFilterController {

	Logger logger = LoggerFactory.getLogger(SearchFilterController.class);

	@Autowired
	private SearchDeliveryScheduleService searchDeliveryScheduleService;

	 @Operation(summary = SwaggerConstants.SEARCH_SCHEDULE_NOTES,description = SwaggerConstants.SEARCH_SCHEDULE_VALUE,
		     responses = {
		@ApiResponse(responseCode = SwaggerConstants.STATUS_SUCCESS, description = SwaggerConstants.SUCCESS_OK,content = @Content(mediaType = "application/json",
				array =@ArraySchema(schema = @Schema(implementation = SearchScheduleRO.class)))),
		@ApiResponse(responseCode = SwaggerConstants.STATUS_UNAUTHORIZED, description = SwaggerConstants.UNAUTHORIZED) ,
		@ApiResponse(responseCode = SwaggerConstants.STATUS_NOTFOUND, description = SwaggerConstants.NOT_FOUND),
		@ApiResponse(responseCode = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR, description = SwaggerConstants.INTERNAL_SERVER_ERROR)})
	@PostMapping("/list/{majorDept}/{namedScheduleId}")
	public Response retrieveCurrentSchedule(@RequestParam(name = "district", required = false) String district,
			@RequestParam(name = "store", required = false) Integer store, @PathVariable String majorDept,
			@PathVariable int namedScheduleId, @RequestBody AdvancedFilters advancedFilters) {

		List<SearchScheduleRO> dtos = searchDeliveryScheduleService.retrieveSchedule(district, store, majorDept,
				namedScheduleId, advancedFilters);
		if (dtos.isEmpty()) {
			logger.info("Search Schedule list is not found");
			return new Response(HttpStatus.OK.value(), "Search Schedule list is empty.", dtos);
		} else {
			logger.info("Search Schedule listt is fetched successfully");
			return new Response(HttpStatus.OK.value(), "Search Schedule fetched successfully.", dtos);
		}

	}
}
