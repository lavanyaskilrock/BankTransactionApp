package com.project.BankTransactionApp.account.repository;

import com.project.BankTransactionApp.account.entity.AccountMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountMappingRepository extends JpaRepository <AccountMapping,Long>{
    List<AccountMapping> findByAccountId(Long accountId);
}
