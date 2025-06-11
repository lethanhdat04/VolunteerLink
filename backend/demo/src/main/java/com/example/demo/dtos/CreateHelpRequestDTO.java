package com.example.demo.dtos;

import com.example.demo.enums.PriorityEnum;
import com.example.demo.enums.SkillEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class CreateHelpRequestDTO {
    private Integer id;
    private Integer recipientId;
    private String title;
    private String description;
    private Set<SkillEnum> requiredSkills;
    private String location;
    private PriorityEnum priority;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
