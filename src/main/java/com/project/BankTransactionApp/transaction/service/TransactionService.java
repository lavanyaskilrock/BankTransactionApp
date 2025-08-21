package com.project.BankTransactionApp.transaction.service;

import com.project.BankTransactionApp.account.entity.AccountMapping;
import com.project.BankTransactionApp.account.repository.AccountMappingRepository;
import com.project.BankTransactionApp.exception.AccountNotFoundException;
import com.project.BankTransactionApp.exception.InvalidAmountException;
import com.project.BankTransactionApp.exception.InvalidCredentialsException;
import com.project.BankTransactionApp.transaction.TransactionType;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public Transaction withdraw(String username, Transaction transaction){
        if(transaction.getAmount() == null || transaction.getAmount()<= 0 ){
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
        if(existingAccount.getBalance()- transaction.getAmount() <0 ){
            throw new InvalidAmountException("Insufficient balance");
        }
        existingAccount.setBalance(existingAccount.getBalance()- transaction.getAmount());
        accountMappingRepository.save(existingAccount);
        transaction.setTime(LocalDateTime.now());
        transaction.setAccountType(existingAccount.getAccountType());
        transaction.setTransactionFrom(null);
        transaction.setTransactionTo(existingAccount);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        return transactionRepository.save(transaction);
    }
    public Transaction transfer(String username, Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount() <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        AccountMapping fromAccountReq = transaction.getTransactionFrom();
        AccountMapping toAccountReq = transaction.getTransactionTo();
        if (fromAccountReq == null || fromAccountReq.getId() == null ||
                toAccountReq == null || toAccountReq.getId() == null) {
            throw new InvalidCredentialsException("Invalid account id(s)");
        }
        AccountMapping fromAccount=accountMappingRepository.findById(fromAccountReq.getId()).orElseThrow(()->new InvalidCredentialsException("Sender account not found"));
        AccountMapping toAccount=accountMappingRepository.findById(toAccountReq.getId()).orElseThrow(()->new InvalidCredentialsException("Receiver account not found"));
        if (!fromAccount.getAccount().getUser().getUsername().equals(username)){
            throw new InvalidCredentialsException("From account does not belong to user");
        }
        if (fromAccount.getBalance() - transaction.getAmount() < 0){
            throw new InvalidAmountException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance()-transaction.getAmount());
        toAccount.setBalance(toAccount.getBalance()+transaction.getAmount());
        accountMappingRepository.save(fromAccount);
        accountMappingRepository.save(toAccount);
        Transaction transferTransaction = new Transaction();
        transferTransaction.setAmount(transaction.getAmount());
        transferTransaction.setTime(LocalDateTime.now());
        transferTransaction.setAccountType(fromAccount.getAccountType());
        transferTransaction.setTransactionType(TransactionType.TRANSFER);
        transferTransaction.setTransactionFrom(fromAccount);
        transferTransaction.setTransactionTo(toAccount);
        return transactionRepository.save(transferTransaction);
    }
    public List<Transaction> getTransactionsByAccountId(String username,Long accountId){
        AccountMapping account=accountMappingRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(!account.getAccount().getUser().getUsername().equals(username)){
            throw new InvalidCredentialsException("Account does not belong to you, Permission Denied");
        }
        return transactionRepository.findByTransactionFromIdOrTransactionToId(accountId,accountId);
    }
}

