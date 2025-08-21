package com.project.BankTransactionApp.transaction.repository;

import com.project.BankTransactionApp.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction,Long> {
    List<Transaction> findByTransactionFromIdOrTransactionToId(Long fromId, Long toId);
}
