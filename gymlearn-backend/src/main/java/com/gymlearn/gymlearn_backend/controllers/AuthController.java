package com.gymlearn.gymlearn_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gymlearn.gymlearn_backend.DTO.RegisterDTO;
import com.gymlearn.gymlearn_backend.DTO.UserDTO;
import com.gymlearn.gymlearn_backend.model.User;
import com.gymlearn.gymlearn_backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody RegisterDTO registerDTO){
        User newUser = userService.createUser(registerDTO.name(), registerDTO.email(), registerDTO.password(), registerDTO.role());

        UserDTO userDTO = new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
