package com.example.demo.services;

import com.example.demo.dtos.RegisterUserDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.enities.*;
import com.example.demo.enums.RoleEnum;
import com.example.demo.enums.UserStatusEnum;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.DtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, DtoMapper dtoMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> findByRole(RoleEnum roleEnum) {
        Role role = roleRepository.findByName(roleEnum).orElseThrow(() -> new RuntimeException("Role not found"));
        List<User> users = userRepository.findByRole(role);
        return dtoMapper.mapList(users, UserDTO.class);
    }

    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        return dtoMapper.mapList(users, UserDTO.class);
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return dtoMapper.map(user, UserDTO.class);
    }

    public UserDTO saveUser(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user;

        switch (registerUserDTO.getRole().getId()) {
            case 3:
                Volunteer volunteer = new Volunteer();
                volunteer.setFullName(registerUserDTO.getFullName());
                volunteer.setEmail(registerUserDTO.getEmail());
                volunteer.setPassword(registerUserDTO.getPassword());
                volunteer.setRole(registerUserDTO.getRole());
                volunteer.setStatus(UserStatusEnum.ACTIVE);
                user = volunteer;
                break;

            case 2:
                Organization organization = new Organization();
                organization.setFullName(registerUserDTO.getFullName());
                organization.setEmail(registerUserDTO.getEmail());
                organization.setPassword(registerUserDTO.getPassword());
                organization.setRole(registerUserDTO.getRole());
                organization.setStatus(UserStatusEnum.ACTIVE);
                user = organization;
                break;

            case 4:
                Recipient recipient = new Recipient();
                recipient.setFullName(registerUserDTO.getFullName());
                recipient.setEmail(registerUserDTO.getEmail());
                recipient.setPassword(registerUserDTO.getPassword());
                recipient.setRole(registerUserDTO.getRole());
                recipient.setStatus(UserStatusEnum.ACTIVE);
                user = recipient;
                break;

            default:
                throw new IllegalArgumentException("Invalid role");
        }

        User updateUser = userRepository.save(user);
        return dtoMapper.map(updateUser, UserDTO.class);
    }
}
