package com.astefanski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with that name or id does not exists!")
public class CustomerUserDoesNotExistsException extends RuntimeException {

}