package com.finance.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.tracker.domain.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer>{

}
