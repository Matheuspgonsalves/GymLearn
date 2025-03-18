package com.gymlearn.gymlearn_backend.DTO;

import com.gymlearn.gymlearn_backend.model.Role;

public record RegisterDTO(String name, String email, String password, Role role) {

}
