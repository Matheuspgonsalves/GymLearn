package com.gymlearn.gymlearn_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymlearn.gymlearn_backend.DTO.LoginDTO;
import com.gymlearn.gymlearn_backend.DTO.RegisterDTO;
import com.gymlearn.gymlearn_backend.DTO.UserDTO;
import com.gymlearn.gymlearn_backend.model.User;
import com.gymlearn.gymlearn_backend.service.TokenService;
import com.gymlearn.gymlearn_backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager){
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody RegisterDTO registerDTO){ // Registro do usuário
        User newUser = userService.createUser(registerDTO.name(), registerDTO.email(), registerDTO.password(), registerDTO.role());

        UserDTO userDTO = new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) { // Autenticação do login com JWT
        //Criar um objeto de autenticação com email e senha
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());

        //Verificar se as credenciais são válidas
        authenticationManager.authenticate(authToken);

        //Buscar os detalher do usuário que foi autenticado
        UserDetails userDetails = userService.loadUserByUsername(loginDTO.email());

        //Gera o JWT
        String jwtToken = tokenService.generateToken(userDetails);

        //Retorna o token 
        return ResponseEntity.ok(jwtToken);
    }
    
}
