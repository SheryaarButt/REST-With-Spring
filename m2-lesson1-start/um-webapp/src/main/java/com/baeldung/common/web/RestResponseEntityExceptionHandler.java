package com.baeldung.common.web;

import com.baeldung.common.web.exception.ApiError;
import com.baeldung.common.web.exception.MyBadRequestException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // API

    // 400

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, message(status, ex) , headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, message(status, ex) , headers, status, request);
    }

    @ExceptionHandler(value = { JdbcSQLIntegrityConstraintViolationException.class, MyBadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, message(HttpStatus.BAD_REQUEST, ex) , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ApiError message(final HttpStatus status, final Exception exception) {
        final int apiStatus = status.value();
        final String message = exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage();
        final String devMessage = ExceptionUtils.getRootCauseMessage(exception);

        return new ApiError(apiStatus, message, devMessage);
    }
}
