package com.watchShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
//	//komt uit Package org.springframework.data.rest.webmvc. Class ResourceNotFoundException.
//	@ExceptionHandler(ResourceNotFoundException.class)
//	 public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//            .body(ex.getMessage());
//    }
}