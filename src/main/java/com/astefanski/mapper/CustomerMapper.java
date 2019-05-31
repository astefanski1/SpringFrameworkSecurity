package com.astefanski.mapper;

import com.astefanski.dto.CustomerDTO;
import com.astefanski.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public List<CustomerDTO> map(List<User> users) {
        return users.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public CustomerDTO map(User user) {
        return CustomerDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountType(user.getAccount().getAccountType())
                .street(user.getAddress().getStreet())
                .city(user.getAddress().getCity())
                .postcode(user.getAddress().getPostcode())
                .build();
    }
}
