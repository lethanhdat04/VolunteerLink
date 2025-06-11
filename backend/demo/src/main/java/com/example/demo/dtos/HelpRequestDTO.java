package com.example.demo.dtos;

import com.example.demo.enities.HelpRequest;
import com.example.demo.enums.HelpRequestStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HelpRequestDTO {
    private Integer id;
    private String title;
    private String description;
    private HelpRequestStatusEnum status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private Integer recipientId;
    // private String recipientName;
    private Integer organizationId;
    // private String organizationName;
    private Integer volunteerId;
    // private String volunteerName;

//    public HelpRequestDTO(HelpRequest helpRequest) {
//        this.id = helpRequest.getId();
//        this.title = helpRequest.getTitle();
//        this.description = helpRequest.getDescription();
//        this.status = helpRequest.getStatus();
//        this.createAt = helpRequest.getCreateAt();
//        this.updateAt = helpRequest.getUpdateAt();
//
//        if (helpRequest.getRecipient() != null) {
//            this.recipientId = helpRequest.getRecipient().getId();
//            this.recipientName = helpRequest.getRecipient().getFullName();
//        }
//
//        if (helpRequest.getOrganization() != null) {
//            this.organizationId = helpRequest.getOrganization().getId();
//            this.recipientName = helpRequest.getOrganization().getFullName();
//        }
//
//        if (helpRequest.getVolunteer() != null) {
//            this.volunteerId = helpRequest.getVolunteer().getId();
//            this.volunteerName = helpRequest.getVolunteer().getFullName();
//        }
//    }
}
