package com.astefanski.controller;

import com.astefanski.dto.CustomerDTO;
import com.astefanski.dto.TransactionDTO;
import com.astefanski.exceptions.CustomerUserDoesNotExistsException;
import com.astefanski.mapper.CustomerMapper;
import com.astefanski.model.User;
import com.astefanski.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @PutMapping("/transaction")
    public String sendMoney(@RequestBody TransactionDTO transactionDTO, Principal principal) {

        return customerService.createTransaction(transactionDTO, principal.getName());
    }

    @GetMapping("/userAccount")
    public String getUserAccountInformation(Principal principal) {
        User user = customerService.getUserByName(principal.getName()).orElseThrow(CustomerUserDoesNotExistsException::new);

        return "Money amount " + user.getAccount().getAvailableBalance();
    }


    @PutMapping("/editName")
    public ResponseEntity<?> changeName(@RequestBody String newName, Principal principal) {
        return ResponseEntity.accepted().body(customerMapper.map(customerService.changeName(principal.getName(), newName)));
    }
}
