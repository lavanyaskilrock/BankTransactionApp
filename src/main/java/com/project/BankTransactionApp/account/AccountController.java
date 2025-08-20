package com.project.BankTransactionApp.account;

import com.project.BankTransactionApp.security.JwtUtil;
import com.project.BankTransactionApp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
