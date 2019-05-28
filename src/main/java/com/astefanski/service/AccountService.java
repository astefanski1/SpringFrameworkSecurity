package com.astefanski.service;

import com.astefanski.model.Account;
import com.astefanski.model.AccountType;
import com.astefanski.model.Bank;
import com.astefanski.model.User;
import com.astefanski.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AccountService {

    @Resource
    private AccountRepository accountRepository;

    public Account createNewAccount(AccountType accountType, Bank bank) {
        Account account = Account.builder()
                .accountType(accountType)
                .pendingBalance(0)
                .availableBalance(0)
                .lastActive(LocalDateTime.now())
                .accountBelongsToBank(bank)
                .build();

        accountRepository.save(account);
        return account;
    }

    public Account assignCustomerToAccount(User user, Account account, Bank bank) {
        account.setUserAccount(user);
        account.setAccountBelongsToBank(bank);

        return accountRepository.save(account);
    }
}
