package com.astefanski.controller;

import com.astefanski.dto.FieldErrorDTO;
import com.astefanski.exceptions.ValidationException;
import com.astefanski.mapper.FieldErrorMapper;
import com.astefanski.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AbstractController {

    private final FieldErrorMapper fieldErrorMapper = new FieldErrorMapper();

    private static final String FORBIDDEN = "This operation is forbidden. You have no access to this resource.";

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handleRecordValidationException(ValidationException ex) {
        log.trace(ex.getMessage(), ex);
        return ex.getErrorResponse();
    }

    public List<String> validModel(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    public void validateFieldErrors(Errors errors) throws ValidationException {
        if (errors.hasErrors()) {
            List<FieldErrorDTO> fieldsErrors = fieldErrorMapper.map(errors.getFieldErrors());
            throw new ValidationException(new ErrorResponse(fieldsErrors));
        }
    }

}