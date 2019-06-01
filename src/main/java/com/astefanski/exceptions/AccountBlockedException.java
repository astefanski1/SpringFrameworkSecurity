package com.astefanski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Account blocked.")
public class AccountBlockedException extends RuntimeException {

}