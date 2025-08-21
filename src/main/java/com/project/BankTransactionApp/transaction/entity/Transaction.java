package com.project.BankTransactionApp.transaction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.BankTransactionApp.account.entity.AccountMapping;
import com.project.BankTransactionApp.account.AccountType;
import com.project.BankTransactionApp.transaction.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="transaction_from_account_id",referencedColumnName = "id")
    private AccountMapping transactionFrom;
    @ManyToOne
    @JoinColumn(name="transaction_to_account_id",referencedColumnName = "id")
    private AccountMapping transactionTo;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime time;
    private Double amount;
}
