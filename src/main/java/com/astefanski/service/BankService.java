package com.astefanski.service;

import com.astefanski.model.Account;
import com.astefanski.model.Bank;
import com.astefanski.model.User;
import com.astefanski.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class BankService {

    @Resource
    private BankRepository bankRepository;

    public Bank getBank() {
        return bankRepository.findFirstByOrderByIdAsc();
    }

    public Bank addCustomerToBank(User user, Bank bank, Account account) {
        bank.getUsers().add(user);
        bank.getAccounts().add(account);
        return bankRepository.save(bank);
    }


}
