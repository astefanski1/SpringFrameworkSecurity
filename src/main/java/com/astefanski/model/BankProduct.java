package com.astefanski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productID;

    @NotNull
    private ProductType productType;

    @NotNull
    private LocalDateTime dateBought;

    @NotNull
    private LocalDateTime dateRetired;

    //CUSTOMER
    @ManyToMany
    @JoinTable(name = "UsersHasProducts",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idBankProduct"))
    private List<User> users = new ArrayList<>();

}