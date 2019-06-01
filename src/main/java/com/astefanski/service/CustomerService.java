package com.astefanski.service;

import com.astefanski.dto.TransactionDTO;
import com.astefanski.dto.UserBlockedDTO;
import com.astefanski.exceptions.CustomerUserDoesNotExistsException;
import com.astefanski.mapper.UserBlockedMapper;
import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserBlockedMapper userBlockedMapper;

    public String createTransaction(TransactionDTO transactionDTO, String accountOwnerName) {
        User accountOwner = customerRepository.findByNameOrEmail(accountOwnerName, accountOwnerName).orElseThrow(CustomerUserDoesNotExistsException::new);
        User moneyToUser = customerRepository.findByNameOrEmail(transactionDTO.getMoneyToUser(), transactionDTO.getMoneyToUser()).orElseThrow(CustomerUserDoesNotExistsException::new);


        accountService.createNewAccountTransaction(accountOwner, moneyToUser, transactionDTO.getMoneyAmount());
        accountService.sendMoney(accountOwner.getAccount(), moneyToUser.getAccount(), transactionDTO.getMoneyAmount());

        return "Money send from (" + accountOwnerName + ") to (" + moneyToUser.getName() + ") amount: " + transactionDTO.getMoneyAmount();
    }

    public Optional<User> getUserByName(String name) {
        return customerRepository.findByNameOrEmail(name, name);
    }

    public Optional<User> getUserByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public User changeName(String oldUserName, String newName) {
        customerRepository.findByNameOrEmail(oldUserName, oldUserName).ifPresent(user -> {
            user.setName(newName);
            customerRepository.save(user);
        });

        return customerRepository.findByNameOrEmail(newName, newName).orElseThrow(CustomerUserDoesNotExistsException::new);
    }

    public UserBlockedDTO getUserBlockedInformation(String username) {
        return userBlockedMapper.map(customerRepository.findByUsername(username).orElseThrow(CustomerUserDoesNotExistsException::new));
    }

}