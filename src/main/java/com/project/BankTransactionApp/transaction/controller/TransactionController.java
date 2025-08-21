package com.project.BankTransactionApp.transaction.controller;

import com.project.BankTransactionApp.security.JwtUtil;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import com.project.BankTransactionApp.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {


   private Transaction transaction;
   private TransactionService transactionService;
   private TransactionRepository transactionRepository;
   private JwtUtil jwtUtil;

    public TransactionController( TransactionService transactionService, TransactionRepository transactionRepository, JwtUtil jwtUtil) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Transaction transaction, HttpServletRequest request) {
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
            Transaction createTransaction = transactionService.deposit(username, transaction);
            return ResponseEntity.ok(createTransaction);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error during transaction " + e.getMessage());
        }

    }
//    @PostMapping("/withdraw")
//    public ResponseEntity<?> createAccount(@RequestBody Account account, HttpServletRequest request) {
//        String authHeader=request.getHeader("Authorization");
//        if(authHeader==null||!authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(401).body("Auth token required");
//        }
//        String token=authHeader.substring(7);
//        if(!jwtUtil.validateToken(token)){
//            return ResponseEntity.status(401).body("Invalid token");
//        }
//        String username= jwtUtil.extractUsername(token);
//        Account createdAccount=transactionService.createAccount(username,account);
//        return ResponseEntity.ok(createdAccount);
//    }
//    @PostMapping("/transfer")
//    public ResponseEntity<?> createAccount(@RequestBody Account account, HttpServletRequest request) {
//        String authHeader=request.getHeader("Authorization");
//        if(authHeader==null||!authHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(401).body("Auth token required");
//        }
//        String token=authHeader.substring(7);
//        if(!jwtUtil.validateToken(token)){
//            return ResponseEntity.status(401).body("Invalid token");
//        }
//        String username= jwtUtil.extractUsername(token);
//        Account createdAccount=transactionService.createAccount(username,account);
//        return ResponseEntity.ok(createdAccount);
//    }


}
