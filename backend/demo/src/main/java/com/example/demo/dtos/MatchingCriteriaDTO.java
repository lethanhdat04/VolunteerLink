package com.example.demo.dtos;

import lombok.Data;

@Data
public class MatchingCriteriaDTO {
    private Integer requestId;
    private Double maxDistance;
    private Integer minRating;
    private Boolean prioritizeExperience;
    private Boolean prioritizeDistance;
    private Integer maxResults;
}
