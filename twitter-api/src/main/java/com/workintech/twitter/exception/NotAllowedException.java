package com.workintech.twitter.exception;

import org.springframework.http.HttpStatus;

public class NotAllowedException extends TwitterException {

    public NotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
