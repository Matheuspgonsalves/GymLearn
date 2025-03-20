package com.gymlearn.gymlearn_backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gymlearn.gymlearn_backend.model.Role;
import com.gymlearn.gymlearn_backend.model.User;
import com.gymlearn.gymlearn_backend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String name, String email, String password, Role role){
        if(userRepository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado!");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User(name, email, hashedPassword, role);
        
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));
    }
}
