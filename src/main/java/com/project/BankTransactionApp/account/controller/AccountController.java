package com.project.BankTransactionApp.account.controller;

import com.project.BankTransactionApp.account.entity.Account;
import com.project.BankTransactionApp.account.entity.AccountMapping;
import com.project.BankTransactionApp.account.repository.AccountRepository;
import com.project.BankTransactionApp.account.service.AccountService;
import com.project.BankTransactionApp.security.JwtUtil;
import com.project.BankTransactionApp.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private Account account;

    private AccountRepository accountRepository;

    private AccountService accountService;

    private UserRepository userRepository;


    private JwtUtil jwtUtil;

    public AccountController(UserRepository userRepository,  AccountRepository accountRepository, AccountService accountService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account, HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Auth token required");
        }
            String token=authHeader.substring(7);
            if(!jwtUtil.validateToken(token)){
                return ResponseEntity.status(401).body("Invalid token");
            }
            String username= jwtUtil.extractUsername(token);
            Account createdAccount=accountService.createAccount(username,account);
            return ResponseEntity.ok(createdAccount);
    }
    @GetMapping("/accounts")
    public ResponseEntity<?> getUserAccounts(HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Auth token required"); // Add missing return
        }
        String token=authHeader.substring(7);
        if(!jwtUtil.validateToken(token)){
            return ResponseEntity.status(401).body("Invalid token");
        }
        String username= jwtUtil.extractUsername(token);
        List<Account> accounts=accountService.getUserAccounts(username); // Change type
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountMappingById(@PathVariable Long id, HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Auth token required");
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            String username = jwtUtil.extractUsername(token);
            AccountMapping accountMapping = accountService.getAccountMappingById(username, id);
            return ResponseEntity.ok(accountMapping);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving account: " + e.getMessage());
        }
    }
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> closeAccount(@PathVariable Long id, HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Auth token required");
            }
            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid token");
            }
            String username = jwtUtil.extractUsername(token);
            accountService.closeAccount(username, id);
            return ResponseEntity.ok("Account closed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error closing account: " + e.getMessage());
        }
    }
}
