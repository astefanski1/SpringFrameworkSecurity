package com.astefanski.model;

import com.astefanski.converter.CustomerPasswordConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "{spring.messages.emptyFirstName}")
    private String name;

    @Column(unique = true)
    @NotNull(message = "{spring.messages.emptyUsername}")
    @Size(min = 3, max = 100, message = "{spring.messages.wrongUsername}")
    private String username;

    @Column(unique = true)
    @NotNull(message = "{spring.messages.emptyEmail}")
    @Email(message = "{spring.messages.wrongEmail}")
    @Size(max = 60, message = "{spring.messages.longEmail}")
    private String email;

    @Builder.Default
    private boolean blocked = false;

    public User(User user) {
        this.email = user.getEmail();
        this.role = user.getRole();
        this.name = user.getName();
        this.id = user.getId();
        this.password = user.getPassword();
    }

    @NotNull(message = "{spring.messages.emptyPassword}")
    @Size(min = 3, max = 100, message = "{spring.messages.wrongPassword")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Convert(converter = CustomerPasswordConverter.class)
    private String password;

    @Override
    public String toString() {
        return "\nName: " + name +
                "\nEmail: " + email +
                "\nUsername: " + username +
                "\nPassword: " + password +
                "\n----Address---- " + address +
                "\n----Accounts---- " + account +
                "\n----Bank---- " + bank;

    }

    //ROLE
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "idRole")
    @JsonIgnore
    private Role role;

    //ADDRESS
    @OneToOne(mappedBy = "userAddress", fetch = FetchType.EAGER)
    private Address address;

    //ACCOUNTS
    @OneToOne(mappedBy = "userAccount", fetch = FetchType.EAGER)
    private Account account;

    //BANK
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "idBank")
    private Bank bank;

    //PRODUCTS
    @ManyToMany
    @JoinTable(name = "UserHasProducts",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idBankProduct"))
    private List<BankProduct> bankProducts = new ArrayList<>();

}