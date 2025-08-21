package com.project.BankTransactionApp.transaction.service;

import com.project.BankTransactionApp.account.entity.AccountMapping;
import com.project.BankTransactionApp.account.repository.AccountMappingRepository;
import com.project.BankTransactionApp.exception.InvalidAmountException;
import com.project.BankTransactionApp.exception.InvalidCredentialsException;
import com.project.BankTransactionApp.transaction.TransactionType;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountMappingRepository accountMappingRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountMappingRepository accountMappingRepository) {
        this.transactionRepository = transactionRepository;
        this.accountMappingRepository = accountMappingRepository;
    }
    public Transaction deposit(String username, Transaction transaction){
        if(transaction.getAmount() == null || transaction.getAmount()<=0 ){
            throw new InvalidAmountException("Amount can not be less than 0");
        }

        AccountMapping accountMapping= transaction.getTransactionFrom();
        if(accountMapping.getId() ==null){
            throw new InvalidCredentialsException("Invalid account id");
        }
        Optional<AccountMapping> accountMappingOptional=accountMappingRepository.findById(accountMapping.getId());
        if (!accountMappingOptional.isPresent()) {
            throw new InvalidCredentialsException("Account not found");
        }
        AccountMapping existingAccount=accountMappingOptional.get();
        if(!existingAccount.getAccount().getUser().getUsername().equals(username)){
            throw new InvalidCredentialsException("Account does not belong to user");
        }
        existingAccount.setBalance(existingAccount.getBalance()+ transaction.getAmount());
        accountMappingRepository.save(existingAccount);
        transaction.setTime(LocalDateTime.now());
        transaction.setAccountType(existingAccount.getAccountType());
        transaction.setTransactionFrom(null);
        transaction.setTransactionTo(existingAccount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        return transactionRepository.save(transaction);
    }
}
