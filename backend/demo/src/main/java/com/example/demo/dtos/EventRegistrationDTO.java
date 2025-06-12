package com.example.demo.dtos;

import com.example.demo.enums.RegistrationStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRegistrationDTO {
    private Integer id;
    private Integer eventId;
    private Integer volunteerId;
    private LocalDateTime registrationTime;
    @Enumerated(EnumType.STRING)
    private RegistrationStatusEnum status;

    private Integer rating;

//    private String review;
//
//    private LocalDateTime createAt;
//    private LocalDateTime updateAt;
}
