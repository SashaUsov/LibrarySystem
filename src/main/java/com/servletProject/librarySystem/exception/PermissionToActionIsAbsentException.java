package com.servletProject.librarySystem.exception;

public class PermissionToActionIsAbsentException extends BusinessExceptions {

    public PermissionToActionIsAbsentException(String message) {
        super(message);
    }

    public PermissionToActionIsAbsentException(Exception ex){
        super(ex);
    }

    public PermissionToActionIsAbsentException(){
        super();
    }
}
