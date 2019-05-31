package com.servletProject.librarySystem.exception;

public class ClientAlreadyExistsException extends BusinessExceptions {

    public ClientAlreadyExistsException(String message) {
        super(message);
    }

    public ClientAlreadyExistsException(Exception ex){
        super(ex);
    }

    public ClientAlreadyExistsException(){
        super();
    }
}
