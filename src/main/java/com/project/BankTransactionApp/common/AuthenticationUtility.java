package com.project.BankTransactionApp.common;

import com.project.BankTransactionApp.common.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component
public class AuthenticationUtility {
    private JwtUtil jwtUtil;
    @Autowired
    public AuthenticationUtility(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String authenticateToken(HttpServletRequest request){
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return null;
            }
            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                return null;
            }
            return jwtUtil.extractUsername(token);
    }
    public ResponseEntity<?> createInvalidTokenResponse(){
        return ResponseEntity.status(401).body("Invalid token");
    }
}
