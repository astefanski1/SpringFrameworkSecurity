package com.astefanski.service;

import com.astefanski.model.Account;
import com.astefanski.model.AccountTransaction;
import com.astefanski.model.AccountType;
import com.astefanski.model.Bank;
import com.astefanski.model.User;
import com.astefanski.repository.AccountRepository;
import com.astefanski.repository.AccountTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AccountService {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private AccountTransactionRepository accountTransactionRepository;

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

    public void sendMoney(Account fromAccount, Account toAccount, double amount) {
        fromAccount.setAvailableBalance(fromAccount.getAvailableBalance() - amount);
        toAccount.setAvailableBalance(toAccount.getAvailableBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    public AccountTransaction createNewAccountTransaction(User accountOwner, User moneyToUser, double amount) {
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .accountFrom(accountOwner.getId())
                .accountTo(moneyToUser.getId())
                .transactionInBank(accountOwner.getBank())
                .amount(amount)
                .localDateTime(LocalDateTime.now())
                .build();

        return accountTransactionRepository.save(accountTransaction);
    }

}