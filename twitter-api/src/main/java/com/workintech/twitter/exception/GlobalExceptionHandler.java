package com.workintech.twitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TwitterException.class)
    public ResponseEntity<ErrorResponse> twitterExceptionHandler(TwitterException twitterException){

        ErrorResponse errorResponse =new ErrorResponse(twitterException.getMessage());
        return new ResponseEntity<>(errorResponse,twitterException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){

        ErrorResponse errorResponse =new ErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
