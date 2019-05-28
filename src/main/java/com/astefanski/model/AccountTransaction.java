package com.astefanski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionID")
    private long transactionID;

    @Column(name = "amount")
    @NotNull
    private double amount;

    @Column(name = "transaction_date")
    @NotNull
    private LocalDateTime localDateTime;

    @Column(name = "accountFrom")
    @NotNull
    private Long accountFrom;

    @Column(name = "accountTo")
    @NotNull
    private Long accountTo;

    //BANK
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idBank")
    private Bank transactionInBank;

}
