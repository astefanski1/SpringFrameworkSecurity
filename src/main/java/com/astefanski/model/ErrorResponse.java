package com.astefanski.model;

import com.astefanski.dto.FieldErrorDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
public class ErrorResponse {

    private String error;

    private List<FieldErrorDTO> fieldsErrors;

    public ErrorResponse(String error) {
        super();
        this.error = error;
    }

    public ErrorResponse(List<FieldErrorDTO> fieldsErrors) {
        super();
        this.error = "Errors in fields";
        this.fieldsErrors = fieldsErrors;
    }

    public ErrorResponse(FieldErrorDTO... fieldsErrors) {
        this(Arrays.asList(fieldsErrors));
    }

}