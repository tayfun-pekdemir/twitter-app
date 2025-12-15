package com.workintech.twitter.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends TwitterException {

    public DuplicateException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

