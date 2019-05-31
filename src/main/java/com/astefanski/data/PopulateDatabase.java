package com.astefanski.data;

import com.astefanski.model.Account;
import com.astefanski.model.AccountTransaction;
import com.astefanski.model.AccountType;
import com.astefanski.model.Address;
import com.astefanski.model.Bank;
import com.astefanski.model.Role;
import com.astefanski.model.User;
import com.astefanski.repository.AccountRepository;
import com.astefanski.repository.AddressRepository;
import com.astefanski.repository.BankRepository;
import com.astefanski.repository.CustomerRepository;
import com.astefanski.service.EmployeeService;
import com.astefanski.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PopulateDatabase {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @EventListener(ContextRefreshedEvent.class)
    public void populateDatabase() throws IOException, ParseException {
        if (employeeService.getAllCustomers().size() < 1) {
            List<Address> addressesPrepared = new ArrayList<>();

            Role customerRole = Role.builder()
                    .id(1L)
                    .displayName("ROLE_CUSTOMER")
                    .name("CUSTOMER")
                    .users(new ArrayList<>())
                    .build();

            Role adminRole = Role.builder()
                    .id(2L)
                    .displayName("ROLE_ADMIN")
                    .name("ADMIN")
                    .users(new ArrayList<>())
                    .build();

            Role employeeRole = Role.builder()
                    .id(3L)
                    .displayName("ROLE_EMPLOYEE")
                    .name("EMPLOYEE")
                    .users(new ArrayList<>())
                    .build();

            customerRole = roleService.save(customerRole);
            roleService.save(employeeRole);
            roleService.save(adminRole);

            for (int i = 0; i < 10; i++) {
                Address address = Address.builder()
                        .city("G" + i)
                        .postcode(84800 + i)
                        .street("M" + i)
                        .build();

                addressesPrepared.add(address);
            }

            Bank bank = Bank.builder()
                    .name("INF BANK")
                    .users(new ArrayList<>())
                    .accounts(new ArrayList<>())
                    .accountTransactions(new ArrayList<>())
                    .build();

            List<Account> accountsPrepared = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Account account = Account.builder()
                        .accountType(AccountType.STANDARD)
                        .availableBalance(100)
                        .lastActive(LocalDateTime.now())
                        .pendingBalance(0)
                        .build();

                accountsPrepared.add(account);
            }

            List<User> customersPrepared = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                User user = User.builder()
                        .email("a@wp.pl" + i)
                        .name("Aleks" + i)
                        .username("username" + i)
                        .password("123")
                        .build();

                customersPrepared.add(user);
            }

            for (int i = 0; i < 10; i++) {
                addressRepository.save(addressesPrepared.get(i));
                if (i == 0) bankRepository.save(bank);
                accountRepository.save(accountsPrepared.get(i));
                customerRepository.save(customersPrepared.get(i));
            }

            for (int i = 0; i < 10; i++) {
                addressesPrepared.get(i).setUserAddress(customersPrepared.get(i));
                addressRepository.save(addressesPrepared.get(i));

                List<Account> accounts = new ArrayList<>();
                List<AccountTransaction> accountTransactions = new ArrayList<>();
                List<User> users = new ArrayList<>();
                accounts.add(accountsPrepared.get(i));
                users.add(customersPrepared.get(i));
                bank.setAccounts(accounts);
                bank.setAccountTransactions(accountTransactions);
                bank.setUsers(users);
                bankRepository.save(bank);

                accountsPrepared.get(i).setAccountBelongsToBank(bank);
                accountsPrepared.get(i).setUserAccount(customersPrepared.get(i));
                accountRepository.save(accountsPrepared.get(i));

                if (i == 0) {
                    adminRole.getUsers().add(customersPrepared.get(i));
                    roleService.save(adminRole);
                    customersPrepared.get(i).setRole(adminRole);
                } else {
                    customerRole.getUsers().add(customersPrepared.get(i));
                    roleService.save(customerRole);
                    customersPrepared.get(i).setRole(customerRole);
                }
                customersPrepared.get(i).setAccounts(accounts);
                customersPrepared.get(i).setBank(bank);
                customersPrepared.get(i).setBankProducts(new ArrayList<>());
                customersPrepared.get(i).setAddress(addressesPrepared.get(i));
                customerRepository.save(customersPrepared.get(i));
            }

            employeeService.getAllCustomers().forEach(customer1 -> log.info("\n////////////////\n---- CUSTOMER ----\n//////////////// {}", customer1.toString()));
        }
    }
}