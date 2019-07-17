package com.servletProject.librarySystem.exception.handler;

import com.servletProject.librarySystem.domen.dto.ErrorInfo;
import com.servletProject.librarySystem.exception.*;
import com.servletProject.librarySystem.utils.CreateEntityUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class ExceptionHandlerService {

    @ExceptionHandler({UserNotFoundException.class,
            ThereAreNoBooksFoundException.class,
            MethodArgumentNotValidException.class,
            ClientAlreadyExistsException.class,
            DataIsNotCorrectException.class,
            OrderNotExistException.class,
            PermissionToActionIsAbsentException.class
    })
    public final ResponseEntity<ErrorInfo> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleUserNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof ThereAreNoBooksFoundException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ThereAreNoBooksFoundException tanfbe = (ThereAreNoBooksFoundException) ex;

            return handleThereAreNoBooksFoundException(tanfbe, headers, status, request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;

            return handleMethodArgumentNotValidException(manve, headers, status, request);
        } else if (ex instanceof ClientAlreadyExistsException) {
            HttpStatus status = HttpStatus.CONFLICT;
            ClientAlreadyExistsException caee = (ClientAlreadyExistsException) ex;

            return handleClientAlreadyExistsException(caee, headers, status, request);
        } else if (ex instanceof DataIsNotCorrectException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            DataIsNotCorrectException dince = (DataIsNotCorrectException) ex;

            return handleDataIsNotCorrectException(dince, headers, status, request);
        } else if (ex instanceof OrderNotExistException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            OrderNotExistException onee = (OrderNotExistException) ex;

            return handleOrderNotExistException(onee, headers, status, request);
        } else if (ex instanceof PermissionToActionIsAbsentException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            PermissionToActionIsAbsentException onee = (PermissionToActionIsAbsentException) ex;

            return handlePermissionToActionIsAbsentException(onee, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    /**
     * Customize the response for UserNotFoundException.
     */
    private ResponseEntity<ErrorInfo> handleUserNotFoundException(UserNotFoundException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request
    ) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * Customize the response for ThereAreNoBooksFoundException.
     */
    private ResponseEntity<ErrorInfo> handleThereAreNoBooksFoundException(ThereAreNoBooksFoundException ex, HttpHeaders headers,
                                                                          HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * Customize the response for MethodArgumentNotValidException.
     */
    private ResponseEntity<ErrorInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                            HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * Customize the response for ClientAlreadyExistsException.
     */
    private ResponseEntity<ErrorInfo> handleClientAlreadyExistsException(ClientAlreadyExistsException ex, HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * Customize the response for ClientAlreadyExistsException.
     */
    private ResponseEntity<ErrorInfo> handleDataIsNotCorrectException(DataIsNotCorrectException ex, HttpHeaders headers,
                                                                      HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * Customize the response for ClientAlreadyExistsException.
     */
    private ResponseEntity<ErrorInfo> handleOrderNotExistException(OrderNotExistException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * Customize the response for ClientAlreadyExistsException.
     */
    private ResponseEntity<ErrorInfo> handlePermissionToActionIsAbsentException(PermissionToActionIsAbsentException ex, HttpHeaders headers,
                                                                                HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, new ErrorInfo().setTimestamp(CreateEntityUtil.getCurrentTime())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString()), headers, status, request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    private ResponseEntity<ErrorInfo> handleExceptionInternal(Exception ex, ErrorInfo body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
