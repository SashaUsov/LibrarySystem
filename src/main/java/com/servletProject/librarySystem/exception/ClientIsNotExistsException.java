package com.servletProject.librarySystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientIsNotExistsException extends BusinessExceptions {

    public ClientIsNotExistsException(String message) {
        super(message);
    }
}
