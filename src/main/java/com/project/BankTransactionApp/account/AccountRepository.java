package com.project.BankTransactionApp.account;

import com.project.BankTransactionApp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public  interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUserId(Long userId);
    Account findByUser(User user);
    List<Account> findAllByUserId(Long userId);
}
