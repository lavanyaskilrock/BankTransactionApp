package com.project.BankTransactionApp.account;

import com.project.BankTransactionApp.exception.AccessDenied;
import com.project.BankTransactionApp.exception.AccountNotFoundException;
import com.project.BankTransactionApp.exception.UserNotFoundException;
import com.project.BankTransactionApp.user.User;
import com.project.BankTransactionApp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMappingRepository accountMappingRepository;
    public Account createAccount(String username, Account acc) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        acc.setUser(user);
        if (acc.getAccountMappings() != null) {
            for (AccountMapping mapping : acc.getAccountMappings()) {
                mapping.setAccount(acc);
            }
        }
        return accountRepository.save(acc);
    }
    public AccountMapping addAccountType(String username,Long accountId,AccountType type){
        User user=userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException("User Not found"));
        Account account=accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(!account.getUser().getId().equals(user.getId())){
            throw new AccessDenied("Unauthorized");
        }AccountMapping mapping=new AccountMapping();
        mapping.setAccount(account);
        mapping.setAccountType(type);
        return accountMappingRepository.save(mapping);

    }

}
