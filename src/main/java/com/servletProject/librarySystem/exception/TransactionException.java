package com.servletProject.librarySystem.exception;

public class TransactionException extends BusinessExceptions {

    public TransactionException(String message) {
        super(message);
    }

    public  TransactionException(Exception ex){
        super(ex);
    }

    public TransactionException(){
        super();
    }
}
