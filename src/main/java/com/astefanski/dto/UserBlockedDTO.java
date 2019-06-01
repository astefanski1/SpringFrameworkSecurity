package com.astefanski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserBlockedDTO {

    @NotNull
    private String username;

    @NotNull
    private boolean isBlocked;

}