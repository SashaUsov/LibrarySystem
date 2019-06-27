package com.servletProject.librarySystem.exception;

public class UserRoleNotFoundException extends BusinessExceptions {

    public UserRoleNotFoundException(String message) {
        super(message);
    }

    public UserRoleNotFoundException(Exception ex){
        super(ex);
    }

    public UserRoleNotFoundException(){
        super();
    }
}
