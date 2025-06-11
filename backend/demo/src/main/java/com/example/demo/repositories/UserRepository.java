package com.example.demo.repositories;

import com.example.demo.enities.Role;
import com.example.demo.enities.User;
import com.example.demo.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByStatus(UserStatusEnum status);
}
