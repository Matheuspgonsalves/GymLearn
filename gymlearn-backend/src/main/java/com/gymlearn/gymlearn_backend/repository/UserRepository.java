package com.gymlearn.gymlearn_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gymlearn.gymlearn_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
    
    boolean existsByEmail(String email);
}
