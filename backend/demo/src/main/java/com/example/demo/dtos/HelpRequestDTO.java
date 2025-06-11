package com.example.demo.dtos;

import com.example.demo.enities.HelpRequest;
import com.example.demo.enums.HelpRequestStatusEnum;
import com.example.demo.enums.PriorityEnum;
import com.example.demo.enums.SkillEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class HelpRequestDTO {
    private Integer id;
    private String title;
    private String description;
    private HelpRequestStatusEnum status;
    private String location;
    private Double longitude = 21.0;
    private Double latitude = 22.0;
    private Integer estimatedTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private PriorityEnum priority;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private Integer recipientId;
    private String recipientName;
    private Integer organizationId;
    private String organizationName;
    private Integer volunteerId;
    private String volunteerName;

    private Set<SkillEnum> requiredSkills;

    public HelpRequestDTO(HelpRequest helpRequest) {
        this.id = helpRequest.getId();
        this.title = helpRequest.getTitle();
        this.description = helpRequest.getDescription();
        this.status = helpRequest.getStatus();
        this.createAt = helpRequest.getCreateAt();
        this.updateAt = helpRequest.getUpdateAt();
        this.requiredSkills = helpRequest.getRequiredSkills();

        if (helpRequest.getRecipient() != null) {
            this.recipientId = helpRequest.getRecipient().getId();
            this.recipientName = helpRequest.getRecipient().getFullName();
        }

        if (helpRequest.getOrganization() != null) {
            this.organizationId = helpRequest.getOrganization().getId();
            this.recipientName = helpRequest.getOrganization().getFullName();
        }

        if (helpRequest.getVolunteer() != null) {
            this.volunteerId = helpRequest.getVolunteer().getId();
            this.volunteerName = helpRequest.getVolunteer().getFullName();
        }
    }
}
