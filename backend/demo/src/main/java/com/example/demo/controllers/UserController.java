package com.example.demo.controllers;

import com.example.demo.dtos.RegisterUserDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.enities.Role;
import com.example.demo.enities.User;
import com.example.demo.enums.RoleEnum;
import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDTO> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody RegisterUserDTO registerUserDTO) {
        try {
            userService.saveUser(registerUserDTO);
            return ResponseEntity.ok(registerUserDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/role/{roleEnum}")
    public ResponseEntity<List<UserDTO>> getUserByRole(@PathVariable RoleEnum roleEnum) {
        return ResponseEntity.ok(userService.findByRole(roleEnum));
    }
}
