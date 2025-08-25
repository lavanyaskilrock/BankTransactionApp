package com.project.BankTransactionApp.account.controller;

import com.project.BankTransactionApp.account.entity.Account;
import com.project.BankTransactionApp.account.entity.AccountMapping;
import com.project.BankTransactionApp.account.repository.AccountRepository;
import com.project.BankTransactionApp.account.service.AccountService;
import com.project.BankTransactionApp.common.AuthenticationUtility;
import com.project.BankTransactionApp.common.security.JwtUtil;
import com.project.BankTransactionApp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    private Account account;
    private final AuthenticationUtility authenticationUtility;

    private final AccountService accountService;



    @Autowired
    public AccountController( AccountService accountService,AuthenticationUtility authenticationUtility) {
        this.accountService = accountService;
        this.authenticationUtility= authenticationUtility;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account, HttpServletRequest request) {
        String username= authenticationUtility.authenticateToken(request);
        if(username == null){
            return authenticationUtility.createInvalidTokenResponse();
        }
            Account createdAccount=accountService.createAccount(username,account);
            return ResponseEntity.ok(createdAccount);
    }
    @GetMapping("/accounts")
    public ResponseEntity<?> getUserAccounts(HttpServletRequest request){
        String username= authenticationUtility.authenticateToken(request);
        if(username == null){
            return authenticationUtility.createInvalidTokenResponse();
        }
        List<Account> accounts=accountService.getUserAccounts(username);
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountMappingById(@PathVariable Long id, HttpServletRequest request) {
            String username= authenticationUtility.authenticateToken(request);
            if(username == null){
                return authenticationUtility.createInvalidTokenResponse();
            }
            AccountMapping accountMapping = accountService.getAccountMappingById(username, id);
            return ResponseEntity.ok(accountMapping);
    }
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> closeAccount(@PathVariable Long id, HttpServletRequest request) {
            String username= authenticationUtility.authenticateToken(request);
            if(username == null){
                return authenticationUtility.createInvalidTokenResponse();
            }
            accountService.closeAccount(username, id);
            return ResponseEntity.ok(Map.of("message", "Account closed successfully"));
    }
}
