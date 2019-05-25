package com.servletProject.librarySystem.exception;

class BusinessExceptions extends RuntimeException {
    BusinessExceptions(String message) {
        super(message);
    }

    BusinessExceptions(Exception ex) {
        super(ex);
    }

    BusinessExceptions() {
        super();
    }
}
