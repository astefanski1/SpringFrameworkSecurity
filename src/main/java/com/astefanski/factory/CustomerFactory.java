package com.astefanski.factory;

import com.astefanski.dto.CustomerDTO;
import com.astefanski.model.Account;
import com.astefanski.model.AccountType;
import com.astefanski.model.Address;
import com.astefanski.model.Bank;
import com.astefanski.model.RoleType;
import com.astefanski.model.User;
import com.astefanski.service.AccountService;
import com.astefanski.service.AddressService;
import com.astefanski.service.BankService;
import com.astefanski.service.EmployeeService;
import com.astefanski.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomerFactory {

    @Autowired
    private AddressService addressService;

    @Autowired
    private BankService bankService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    public User createCustomer(CustomerDTO customerDTO) {
        Address address = Address.builder()
                .street(customerDTO.getStreet())
                .city(customerDTO.getCity())
                .postcode(customerDTO.getPostcode())
                .build();

        address = addressService.createAddress(address);

        Bank bank = bankService.getBank();

        Account account = accountService.createNewAccount(AccountType.STANDARD, bank);

        User newUser = User.builder()
                .email(customerDTO.getEmail())
                .name(customerDTO.getName())
                .password(customerDTO.getPassword())
                .username(customerDTO.getUsername())
                .build();

        newUser = employeeService.save(newUser);

        //SET CONNECTIONS
        address = addressService.assignCustomerToAddress(address, newUser);
        bank = bankService.addCustomerToBank(newUser, bank, account);
        account = accountService.assignCustomerToAccount(newUser, account, bank);

        newUser.setAddress(address);
        newUser.setRole(roleService.findByName(RoleType.CUSTOMER.toString().toUpperCase()));
        newUser.setBank(bank);
        newUser.setAccount(account);
        newUser.setBankProducts(new ArrayList<>());

        return employeeService.save(newUser);
    }
}
