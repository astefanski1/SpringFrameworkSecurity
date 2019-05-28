package com.astefanski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private int postcode;

    @Override
    public String toString() {
        return "\nStreet: " + street +
                "\nCity: " + city +
                "\nPostCode: " + postcode;
    }

    //CUSTOMER
    @OneToOne
    @PrimaryKeyJoinColumn(name = "idUser")
    private User userAddress;
}