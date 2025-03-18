package com.gymlearn.gymlearn_backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gymlearn.gymlearn_backend.DTO.RegisterDTO;
import com.gymlearn.gymlearn_backend.DTO.UserDTO;
import com.gymlearn.gymlearn_backend.model.User;
import com.gymlearn.gymlearn_backend.repository.UserRepository;
import com.gymlearn.gymlearn_backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody RegisterDTO registerDTO){
        User newUser = userService.createUser(registerDTO.name(), registerDTO.email(), registerDTO.password(), registerDTO.role());

        UserDTO userDTO = new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        User newUser = userService.getUserById(id);

        UserDTO newUserDTO = new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole());

        return ResponseEntity.ok(newUserDTO);
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        User newUser = userService.getUserByEmail(email);

        UserDTO newUserDTO = new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getRole());

        return ResponseEntity.ok(newUserDTO);
    }
}
