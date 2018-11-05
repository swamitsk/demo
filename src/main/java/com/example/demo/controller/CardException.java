package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CardException extends RuntimeException {

    CardException(String exception) {
            super(exception);
        }
 }

