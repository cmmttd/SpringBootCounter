package com.belogrudov.counter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String element_already_exist) {
        super(element_already_exist);
    }

    public BadRequestException() {
    }
}
