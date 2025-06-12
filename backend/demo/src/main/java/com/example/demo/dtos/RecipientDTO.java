package com.example.demo.dtos;

import com.example.demo.enums.UserStatusEnum;
import lombok.Data;

@Data
public class RecipientDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Double longitude;
    private Double latitude;
    private UserStatusEnum status;
}
