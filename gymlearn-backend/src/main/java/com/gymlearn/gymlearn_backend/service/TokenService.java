package com.gymlearn.gymlearn_backend.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
            .subject(userDetails.getUsername()) // Obtém o email
            .issuedAt(new Date()) // Data de emissão do token
            .expiration(new Date(System.currentTimeMillis() + getExpiration())) // Define o tempo que o token vai expirar
            .signWith(Keys.hmacShaKeyFor(getSecret().getBytes(StandardCharsets.UTF_8))) // Ainda não entendi
            .compact(); // Compacta pra gerar uma string
    }

    public String extractUsername(String token){
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(getSecret().getBytes(StandardCharsets.UTF_8))) // Usa a chave pra validar o token
            .build()
            .parseSignedClaims(token) // Decodifica o token
            .getPayload()
            .getSubject(); // Pega o email do usuario
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
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Compara com username do usuário autenticado
    }

}
