package com.servletProject.librarySystem.exception;

public class OrderNotExistException extends BusinessExceptions {

    public OrderNotExistException(String message) {
        super(message);
    }

    public OrderNotExistException(Exception ex){
        super(ex);
    }

    public OrderNotExistException(){
        super();
    }
}
