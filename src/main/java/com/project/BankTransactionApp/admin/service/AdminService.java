package com.project.BankTransactionApp.admin.service;

import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import com.project.BankTransactionApp.user.entity.User;
import com.project.BankTransactionApp.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;
    public AdminService(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository=transactionRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

}
