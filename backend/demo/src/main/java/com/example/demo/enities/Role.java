package com.example.demo.enities;

import com.example.demo.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "roles")
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    private String description;

//    @OneToMany(mappedBy = "role")
//    private List<User> users;
}
