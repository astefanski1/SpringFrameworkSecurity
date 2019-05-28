package com.astefanski.repository;

import com.astefanski.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findFirstByOrderByIdAsc();
}
