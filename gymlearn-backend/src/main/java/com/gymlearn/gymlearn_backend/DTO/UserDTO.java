package com.gymlearn.gymlearn_backend.DTO;

import com.gymlearn.gymlearn_backend.model.Role;

public record UserDTO(Long id, String name, String email, Role role) {

}
