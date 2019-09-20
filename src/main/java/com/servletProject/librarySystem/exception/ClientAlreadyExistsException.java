package com.servletProject.librarySystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientAlreadyExistsException extends BusinessExceptions {

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}
