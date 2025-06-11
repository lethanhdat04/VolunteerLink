package com.example.demo.dtos;

import com.example.demo.enities.Role;
import com.example.demo.enums.SkillEnum;
import com.example.demo.enums.UserStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class VolunteerDTO {
    private Integer id;
    private String fullName;
    private String email;

    // private Role role;

    private Set<SkillEnum> skills;
    private Integer availableHours;
    private Float rating;
    private UserStatusEnum status;
    private String phoneNumber;
    private Double latitude;
    private Double longitude;

//    private Date createdAt;
//    private Date updateAt;
}