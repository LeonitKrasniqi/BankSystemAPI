package com.example.banksystemapi.repository;

import com.example.banksystemapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByOriginatingAccountIdOrResultingAccountId(Integer accountId1, Integer accountId2);
}
