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
import com.raleys.api.cao.odt.schedule.exception.InvalidRequestException;

@RestControllerAdvice
public class InvalidRequestAdvice {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> invalidRequestExceptionHandler(
    		InvalidRequestException invalidRequestException) throws JsonProcessingException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        ErrorMessage message=new ErrorMessage(HttpStatus.BAD_REQUEST.value(), invalidRequestException.getMessage());

        return new ResponseEntity<>(
                new ObjectMapper().writeValueAsString(message),
                httpHeaders,
                HttpStatus.BAD_REQUEST
        );
    }
}
