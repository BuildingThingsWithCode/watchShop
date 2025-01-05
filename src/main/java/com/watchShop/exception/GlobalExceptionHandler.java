package com.watchShop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(WatchNotFoundException.class)
    public ResponseEntity<String> handleWatchNotFoundException(WatchNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
	
	@ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
	
	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException ex){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(IncorrectFilePathException.class)
	public ResponseEntity<String> handleIncorrectFilePathException(IncorrectFilePathException ex){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ex.getMessage());
	}
}