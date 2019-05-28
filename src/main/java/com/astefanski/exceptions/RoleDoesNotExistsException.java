package com.astefanski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Role does not exists!")
public class RoleDoesNotExistsException extends RuntimeException {

}