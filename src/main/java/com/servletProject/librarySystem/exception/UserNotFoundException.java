package com.servletProject.librarySystem.exception;

public class UserNotFoundException extends BusinessExceptions {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Exception ex){
        super(ex);
    }

    public UserNotFoundException(){
        super();
    }
}
