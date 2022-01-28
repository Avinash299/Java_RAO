package com.raleys.api.cao.odt.schedule.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raleys.api.cao.odt.schedule.exception.ErrorMessage;
import com.raleys.api.cao.odt.schedule.exception.FutureScheduleNotUpdatedException;

/**
 * @author swathi.kompella
 * 
 * Advice class for exception handling in future schedule 
 *
 */
@RestControllerAdvice
public class FutureScheduleNotUpdatedAdvice {

    @ExceptionHandler(FutureScheduleNotUpdatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<String> futureScheduleNotUpdatedExceptionHandler(
    		FutureScheduleNotUpdatedException futureScheduleNotUpdatedException) throws JsonProcessingException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        ErrorMessage message=new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), futureScheduleNotUpdatedException.getMessage());

        return new ResponseEntity<>(
                new ObjectMapper().writeValueAsString(message),
                httpHeaders,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
