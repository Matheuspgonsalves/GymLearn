package com.gymlearn.gymlearn_backend.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gymlearn.gymlearn_backend.model.User;
import com.gymlearn.gymlearn_backend.security.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
    private final JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public String getSecret(){
        return jwtConfig.getJwtSecret();
    }

    public Long getExpiration(){
        return jwtConfig.getExpiration();
    }

    public String generateToken(UserDetails userDetails) {
        User user = (User) userDetails;
        return Jwts.builder()
            .subject(userDetails.getUsername()) 
            .claim("role", "ROLE_" + user.getRole().name()) // Garante que a role esteja no payload
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + getExpiration()))
            .signWith(Keys.hmacShaKeyFor(getSecret().getBytes(StandardCharsets.UTF_8)))
            .compact();
    }
    

    public String extractUsername(String token) {
        System.out.println("Tentando extrair username do token: " + token);
        try {
            String username = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(getSecret().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
            
            System.out.println("✅ Username extraído: " + username);
            return username;
        } catch (Exception e) {
            System.out.println("Erro ao extrair username: " + e.getMessage());
            return null;
        }
    }
    

    private boolean isTokenExpired(String token){

        Date expiration = Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(getSecret().getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
        
        return expiration.before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extrai o username (email) do token
        System.out.println("authooooooooooooooooooooooo: " + userDetails.getAuthorities());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Compara com username do usuário autenticado
    }

}
