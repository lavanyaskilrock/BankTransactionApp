package com.project.BankTransactionApp.transaction.controller;

import com.project.BankTransactionApp.security.JwtUtil;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import com.project.BankTransactionApp.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            return ResponseEntity.status(500).body("Error during deposit " + e.getMessage());
        }

    }
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Transaction transaction, HttpServletRequest request) {
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
            Transaction createTransaction = transactionService.withdraw(username, transaction);
            return ResponseEntity.ok(createTransaction);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error during withdrawal" + e.getMessage());
        }

    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Transaction transaction, HttpServletRequest request) {
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
            Transaction createTransaction = transactionService.transfer(username, transaction);
            return ResponseEntity.ok(createTransaction);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error during withdrawal" + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> transfer(@PathVariable Long id,HttpServletRequest request){
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
            List<Transaction> transactions=transactionService.getTransactionsByAccountId(username,id);
            return ResponseEntity.ok(transactions);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Error during withdrawal" + e.getMessage());
        }

    }

}
