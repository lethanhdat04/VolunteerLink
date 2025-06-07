package com.example.demo.dtos;

import com.example.demo.enities.Role;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Integer id;
    private String fullName;
    private String email;
    private Role role;
    private String address;
    private Date createdAt;
    private Date updateAt;
}
