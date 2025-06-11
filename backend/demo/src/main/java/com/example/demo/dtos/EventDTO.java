package com.example.demo.dtos;

import com.example.demo.enums.EventStatusEnum;
import com.example.demo.enums.SkillEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EventDTO {
    private Integer id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<SkillEnum> requiredSkills;
    private Integer maxParticipants;
    private Integer participants;
    private EventStatusEnum status;
    private boolean registered;
//    private Integer organizationId; // or OrganizationDTO if you need nested info
//    private Set<Integer> volunteerIds; // or List<VolunteerDTO> if needed
}
