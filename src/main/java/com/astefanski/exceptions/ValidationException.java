package com.astefanski.exceptions;

import com.astefanski.model.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends Exception {

    private static final long serialVersionUID = -1656239351648079125L;
    private ErrorResponse errorResponse;

    public ValidationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}