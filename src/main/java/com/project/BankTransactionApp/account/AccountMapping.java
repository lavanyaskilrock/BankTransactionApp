package com.project.BankTransactionApp.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="accounts_mapping",
        uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "account_type"}))
public class AccountMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;
    private Double balance=0.0;
    @ManyToOne
    @JoinColumn(name="account_id")
    @JsonBackReference
    private Account account;
}
