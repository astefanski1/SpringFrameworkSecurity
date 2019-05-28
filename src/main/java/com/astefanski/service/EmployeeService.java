package com.astefanski.service;

import com.astefanski.dto.CustomerDTO;
import com.astefanski.factory.CustomerFactory;
import com.astefanski.model.User;
import com.astefanski.repository.CustomerRepository;
import com.astefanski.repository.SQLInjectionEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Resource
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerFactory customerFactory;

    @Resource
    private SQLInjectionEmployeeRepository sqlInjectionEmployeeRepository;

    public User createCustomer(CustomerDTO customerDTO) {
        log.info("Creating customer: {}", customerDTO.getName());

        return customerFactory.createCustomer(customerDTO);
    }

    public User save(User user) {
        return customerRepository.save(user);
    }

    public List<User> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<CustomerDTO> unsafeJpaFindCustomersByCustomerId(String id) throws SQLException {
        return sqlInjectionEmployeeRepository.unsafeJpaFindCustomersByCustomerId(id);
    }

    public List<CustomerDTO> safeFindAccountsByCustomerId(String id) throws SQLException {
        return sqlInjectionEmployeeRepository.safeFindAccountsByCustomerId(id);
    }

    public List<CustomerDTO> safeFindAccountsByCustomerIdUsingJpaCriteria(String id) throws SQLException {
        return sqlInjectionEmployeeRepository.safeFindAccountsByCustomerIdUsingJpaCriteria(id);
    }

    public Optional<User> safeFindByIdUsingJpaRepository(Long id) {
        log.info("Returning user with id: {}", id);

        return customerRepository.findById(id);
    }

    public Optional<User> safeFindByStringUsingJpaRepository(String text) {
        log.info("Returning user with id: {}", text);

        return customerRepository.findByNameOrEmail(text, text);
    }
}