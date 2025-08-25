package com.project.BankTransactionApp.common.security;

import com.project.BankTransactionApp.user.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "THISISASSECRETKEYTHATWASCREATEDBYLAVANYAON19082025";

    public String generateToken(User user)
    {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setId(user.getId().toString())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractRole(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token).getBody().get("role",String.class);
//        return Jwts.parser().setSigningKey(SECRET).
//                parseClaimsJws(token).getBody().get("role",String.class);

    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Long extractUserId(String token){
        return Long.parseLong(Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
                .parseClaimsJws(token)
                .getBody()
                .getId());
    }
}