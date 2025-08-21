package com.project.BankTransactionApp.transaction.repository;

import com.project.BankTransactionApp.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction,Long> {
}
