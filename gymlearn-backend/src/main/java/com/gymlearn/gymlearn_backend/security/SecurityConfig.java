package com.gymlearn.gymlearn_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gymlearn.gymlearn_backend.service.AuthUserDetailsService;
import com.gymlearn.gymlearn_backend.service.TokenService;


@Configuration
public class SecurityConfig {
    private final AuthUserDetailsService authUserDetailsService;

    public SecurityConfig(AuthUserDetailsService authUserDetailsService) {
        this.authUserDetailsService = authUserDetailsService;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return authUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authUserDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, TokenService tokenService, UserDetailsService userDetailsService) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF para testes com Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register", "/auth/login").permitAll()
                .requestMatchers("/api/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER") 
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthFilter(tokenService, userDetailsService), UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro de JWT

        return http.build();
    }


    

}
