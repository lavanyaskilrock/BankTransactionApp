package com.project.BankTransactionApp.account;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name="accounts_mapping")
public class AccountMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Double balance=0.0;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
