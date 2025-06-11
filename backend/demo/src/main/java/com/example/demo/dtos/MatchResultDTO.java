package com.example.demo.dtos;

import lombok.Data;

@Data
public class MatchResultDTO {
    private Integer volunteerId;
    private String volunteerName;
    private Integer requestId;
    private String requestTitle;
    private Double matchScore;
    private String matchReason;
     private Double distance;
}
