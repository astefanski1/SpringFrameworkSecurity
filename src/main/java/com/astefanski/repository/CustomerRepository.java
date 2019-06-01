package com.astefanski.repository;

import com.astefanski.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<User, Long> {

    Optional<User> findByNameOrEmail(String name, String email);

    Optional<User> findByUsername(String username);

}