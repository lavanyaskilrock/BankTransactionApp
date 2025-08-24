package com.project.BankTransactionApp.account.service;

import com.project.BankTransactionApp.account.entity.Account;
import com.project.BankTransactionApp.account.entity.AccountMapping;
import com.project.BankTransactionApp.account.enums.AccountType;
import com.project.BankTransactionApp.account.repository.AccountMappingRepository;
import com.project.BankTransactionApp.account.repository.AccountRepository;
import com.project.BankTransactionApp.common.exception.*;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import com.project.BankTransactionApp.user.entity.User;
import com.project.BankTransactionApp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AccountService {

    private UserRepository userRepository;
    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private AccountMappingRepository accountMappingRepository;

    @Autowired
    public AccountService(UserRepository userRepository, AccountRepository accountRepository, AccountMappingRepository accountMappingRepository,TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountMappingRepository = accountMappingRepository;
        this.transactionRepository=transactionRepository;
    }


    @Transactional
    public Account createAccount(String username, Account acc) {
        User user =userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        acc.setUser(user);
        Account existingAccount=accountRepository.findByUserId(user.getId());
        if(existingAccount!=null) {
            if (acc.getAccountMappings() != null) {
                for (AccountMapping mapping : acc.getAccountMappings()) {
                    mapping.setAccount(existingAccount);
                    accountMappingRepository.save(mapping);
                }
            }
            return existingAccount;
        }
        if(acc.getAccountMappings()!=null){
            for(AccountMapping mapping:acc.getAccountMappings()){
                mapping.setAccount(acc);
            }
        }return accountRepository.save(acc);
    }
    @Transactional
    public AccountMapping addAccountType(String username, Long accountId, AccountType type){
        User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User Not found"));
        Account account=accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(!account.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("Unauthorized");
        }AccountMapping mapping=new AccountMapping();
        mapping.setAccount(account);
        mapping.setAccountType(type);
        return accountMappingRepository.save(mapping);
    }
    public List<Account> getUserAccounts(String username){
        User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User Not Found"));
        System.out.println(" hhh"+user.getId());
        List<Account> accounts = accountRepository.findAllByUserId(user.getId());
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("No accounts found for user");
        }
        return accounts;
    }
    public AccountMapping getAccountMappingById(String username, Long accountMappingId) {
        User user=userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        AccountMapping accountMapping=accountMappingRepository.findById(accountMappingId).orElseThrow(() -> new AccountNotFoundException("Account mapping not found"));
        if (!accountMapping.getAccount().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Unauthorized access to account");
        }
        return accountMapping;
    }
    @Transactional
    public void closeAccount(String username, Long accountMappingId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        AccountMapping accountMapping = accountMappingRepository.findById(accountMappingId).orElseThrow(() -> new AccountNotFoundException("Account mapping not found"));
        if (!accountMapping.getAccount().getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Unauthorized access to account");
        }
        if (accountMapping.getBalance() > 0) {
            throw new InvalidAmountException("Cannot close account with positive balance");
        }
        deleteTransactionsByAccountId(accountMappingId);
        accountMappingRepository.delete(accountMapping);

    }
    @Transactional
    public void deleteTransactionsByAccountId(Long accountMappingId) {
        List<Transaction> transactions = transactionRepository.findByTransactionFromIdOrTransactionToId(accountMappingId, accountMappingId);
        transactionRepository.deleteAll(transactions);
    }

}
