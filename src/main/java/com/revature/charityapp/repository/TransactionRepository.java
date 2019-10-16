package com.revature.charityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.charityapp.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
