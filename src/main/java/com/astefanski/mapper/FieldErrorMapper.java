package com.astefanski.mapper;

import com.astefanski.dto.FieldErrorDTO;
import lombok.NonNull;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class FieldErrorMapper {

    public List<FieldErrorDTO> map(@NonNull List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public FieldErrorDTO map(@NonNull FieldError fieldError) {
        return FieldErrorDTO.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
    }

}