package com.astefanski.controller;

import com.astefanski.dto.TransactionDTO;
import com.astefanski.dto.UserBlockedDTO;
import com.astefanski.exceptions.CustomerUserDoesNotExistsException;
import com.astefanski.mapper.CustomerMapper;
import com.astefanski.model.User;
import com.astefanski.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractController{

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @PutMapping("/transaction")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public String sendMoney(@RequestBody TransactionDTO transactionDTO, Principal principal) {

        return customerService.createTransaction(transactionDTO, principal.getName());
    }

    @GetMapping("/userAccount")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public String getUserAccountInformation(Principal principal) {
        User user = customerService.getUserByName(principal.getName()).orElseThrow(CustomerUserDoesNotExistsException::new);

        return "Money amount " + user.getAccount().getAvailableBalance();
    }

    @GetMapping("/userBlocked")
    public UserBlockedDTO getUserBlockedInformation(@RequestParam String username) {
        return customerService.getUserBlockedInformation(username);
    }


    @PutMapping("/editName")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<?> changeName(@RequestBody String newName, Principal principal) {
        return ResponseEntity.accepted().body(customerMapper.map(customerService.changeName(principal.getName(), newName)));
    }

}