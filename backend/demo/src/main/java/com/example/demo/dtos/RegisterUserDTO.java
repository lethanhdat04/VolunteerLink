package com.example.demo.dtos;

import com.example.demo.enities.Role;
import lombok.Data;

@Data
public class RegisterUserDTO {
    private String email;
    private String password;
    private String fullName;
    private Role role;
}
