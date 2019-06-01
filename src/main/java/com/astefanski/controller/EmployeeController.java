package com.astefanski.controller;

import com.astefanski.dto.CustomerDTO;
import com.astefanski.exceptions.CustomerUserDoesNotExistsException;
import com.astefanski.mapper.CustomerMapper;
import com.astefanski.model.User;
import com.astefanski.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Slf4j
@Api(description = "Operations pertaining to employee", tags = "Employee Controller")
public class EmployeeController extends AbstractController{

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<CustomerDTO> getAllCustomers() {
        return customerMapper.map(employeeService.getAllCustomers());
    }

    @PostMapping
    @ApiOperation(value = "Add new user", response = User.class)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerDTO) {
        User savedUser = employeeService.createCustomer(customerDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}").buildAndExpand(savedUser.getName()).toUri();
        return ResponseEntity.created(location).body(customerMapper.map(savedUser));
    }

    @GetMapping("/unsafe-customer")
    public List<CustomerDTO> unsafeGetCustomerById(String id) throws SQLException {
        return employeeService.unsafeJpaFindCustomersByCustomerId(id);
    }

    @GetMapping("/safe-customer")
    public List<CustomerDTO> safeFindAccountsByCustomerId(String id) throws SQLException {
        return employeeService.safeFindAccountsByCustomerId(id);
    }

    @GetMapping("/safe-customer-criteria")
    public List<CustomerDTO> safeFindAccountsByCustomerIdUsingJpaCriteria(String id) throws SQLException {
        return employeeService.safeFindAccountsByCustomerIdUsingJpaCriteria(id);
    }

    @GetMapping("/safe-customer-jpa")
    public CustomerDTO safeFindAccountsByCustomerIdUsingJpaCriteria(Long id) throws SQLException {
        return customerMapper.map(employeeService.safeFindByIdUsingJpaRepository(id).orElseThrow(CustomerUserDoesNotExistsException::new));
    }

    @GetMapping("/safe-customer-jpa-string/{text}")
    public CustomerDTO safeFindAccountsByCustomerStringUsingJpaCriteria(@PathVariable String text) throws SQLException {
        return customerMapper.map(employeeService.safeFindByStringUsingJpaRepository(text).orElseThrow(CustomerUserDoesNotExistsException::new));
    }
}
