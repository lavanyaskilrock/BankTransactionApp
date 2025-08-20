package com.project.BankTransactionApp.account;

import com.project.BankTransactionApp.Security.JwtUtil;
import com.project.BankTransactionApp.user.User;
import com.project.BankTransactionApp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    private Account account;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Account account, HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer ")) {
            ResponseEntity.status(401).body("Auth token required");
        }
            String token=authHeader.substring(7);
            if(!jwtUtil.validateToken(token)){
                return ResponseEntity.status(401).body("Invalid token");
            }
            String username= jwtUtil.extractUsername(token);
            Account createdAccount=accountService.createAccount(username,account);
            return ResponseEntity.ok(createdAccount);

    }
}
