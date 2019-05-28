package com.astefanski.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;

@Slf4j
public class CustomerPasswordConverter implements AttributeConverter<String, String> {

    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private static final int passwordLength = 60;

    @Override
    public String convertToDatabaseColumn(String password) {
        if (password.length() == passwordLength) {
            return password;
        } else {
            return bCryptPasswordEncoder.encode(password);
        }
    }

    @Override
    public String convertToEntityAttribute(String password) {
        return password;
    }

}