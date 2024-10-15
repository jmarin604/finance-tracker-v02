package com.finance.tracker.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	 @ExceptionHandler(NotFoundException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public ResponseEntity<ErrorMessage> localNotFoundException(NotFoundException exception){
	        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	    }

	    @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	        Map<String,Object> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error ->{
	            errors.put(error.getField(), error.getDefaultMessage());
	        });
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	    }

}
