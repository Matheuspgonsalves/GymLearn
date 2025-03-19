package com.gymlearn.gymlearn_backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "gymlearn.security")
public class JwtConfig {
    private String jwtSecret;
    private Long expiration;

    public String getJwtSecret(){
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret){
        this.jwtSecret = jwtSecret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    
}
