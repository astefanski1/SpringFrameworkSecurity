package com.astefanski.service;

import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    public void createTransaction(User user, String email) {

    }
}