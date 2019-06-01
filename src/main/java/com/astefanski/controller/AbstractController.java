package com.astefanski.controller;

import com.astefanski.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class AbstractController {

    protected static final String FORBIDDEN = "This operation is forbidden. You have no access to this resource.";
    protected static final String BLOCKED = "Your account is blocked.";

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        log.trace(ex.getMessage(), ex);
        return new ErrorResponse(FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {
        log.trace(ex.getMessage(), ex);
        return new ErrorResponse(FORBIDDEN);
    }
}
