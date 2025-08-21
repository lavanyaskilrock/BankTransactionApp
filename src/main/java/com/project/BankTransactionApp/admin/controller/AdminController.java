package com.project.BankTransactionApp.admin.controller;

import com.project.BankTransactionApp.admin.service.AdminService;
import com.project.BankTransactionApp.transaction.entity.Transaction;
import com.project.BankTransactionApp.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=adminService.getAllUsers();
            return ResponseEntity.ok(users);
    }
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions=adminService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
