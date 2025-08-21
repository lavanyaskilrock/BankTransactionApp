package com.project.BankTransactionApp.account.repository;

import com.project.BankTransactionApp.account.entity.Account;
import com.project.BankTransactionApp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUserId(Long userId);
    Account findByUser(User user);
    List<Account> findAllByUserId(Long userId);
}
