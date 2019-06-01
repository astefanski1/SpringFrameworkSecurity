package com.astefanski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountID;

    private double availableBalance;

    private double pendingBalance;

    private LocalDateTime lastActive;

    @NotNull
    private AccountType accountType;

    @Override
    public String toString() {
        return "\nAccount balance: " + availableBalance +
                "\nLast active: " + lastActive +
                "\nAccount Type: " + accountType;
    }

    //CUSTOMER
    @OneToOne
    @PrimaryKeyJoinColumn(name = "idUser")
    private User userAccount;

    //BANK
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idBank")
    private Bank accountBelongsToBank;

}