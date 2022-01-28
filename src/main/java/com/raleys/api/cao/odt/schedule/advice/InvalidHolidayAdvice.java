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
import com.raleys.api.cao.odt.schedule.exception.InvalidVendorException;

@RestControllerAdvice
public class InvalidHolidayAdvice {

    @ExceptionHandler(InvalidVendorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<String> holidayNotFoundExceptionHandler(
    		InvalidVendorException invalidVendorException) throws JsonProcessingException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        ErrorMessage message=new ErrorMessage(HttpStatus.NOT_FOUND.value(), invalidVendorException.getMessage());

        return new ResponseEntity<>(
                new ObjectMapper().writeValueAsString(message),
                httpHeaders,
                HttpStatus.NOT_FOUND
        );
    }
}
