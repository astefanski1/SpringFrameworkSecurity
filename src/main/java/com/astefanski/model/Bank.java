package com.astefanski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Override
    public String toString() {
        return "\nName: " + name;
    }

    //CUSTOMERS
    @OneToMany(mappedBy = "bank")
    private List<User> users = new ArrayList<>();

    //ACCOUNTS
    @OneToMany(mappedBy = "accountBelongsToBank")
    private List<Account> accounts;

    //TRANSACTIONS
    @OneToMany(mappedBy = "transactionInBank")
    private List<AccountTransaction> accountTransactions = new ArrayList<>();
}
