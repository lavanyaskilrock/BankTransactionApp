package com.project.BankTransactionApp.transaction.controller;

import com.project.BankTransactionApp.common.AuthenticationUtility;
import com.project.BankTransactionApp.common.security.JwtUtil;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.transaction.repository.TransactionRepository;
import com.project.BankTransactionApp.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {


   private final TransactionService transactionService;
   private final AuthenticationUtility authenticationUtility;

   @Autowired
    public TransactionController( AuthenticationUtility authenticationUtility,TransactionService transactionService) {
        this.transactionService = transactionService;
        this.authenticationUtility=authenticationUtility;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Transaction transaction, HttpServletRequest request) {
            String username= authenticationUtility.authenticateToken(request);
            if(username == null){
                return authenticationUtility.createInvalidTokenResponse();
            }
            Transaction createTransaction = transactionService.deposit(username, transaction);
            return ResponseEntity.ok(createTransaction);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Transaction transaction, HttpServletRequest request) {
            String username= authenticationUtility.authenticateToken(request);
            if(username == null){
                return authenticationUtility.createInvalidTokenResponse();
            }
            Transaction createTransaction = transactionService.withdraw(username, transaction);
            return ResponseEntity.ok(createTransaction);


    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Transaction transaction, HttpServletRequest request) {
            String username= authenticationUtility.authenticateToken(request);
            if(username == null){
                return authenticationUtility.createInvalidTokenResponse();
            }
            Transaction createTransaction = transactionService.transfer(username, transaction);
            return ResponseEntity.ok(createTransaction);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> transfer(@PathVariable Long id,HttpServletRequest request){
            String username= authenticationUtility.authenticateToken(request);
            if(username == null){
                return authenticationUtility.createInvalidTokenResponse();
            }
            List<Transaction> transactions=transactionService.getTransactionsByAccountId(username,id);
            return ResponseEntity.ok(transactions);

    }

}
