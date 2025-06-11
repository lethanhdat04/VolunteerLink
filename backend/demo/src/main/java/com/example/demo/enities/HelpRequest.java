package com.example.demo.enities;

import com.example.demo.enums.HelpRequestStatusEnum;
import com.example.demo.enums.PriorityEnum;
import com.example.demo.enums.SkillEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class HelpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String location;

    private Double longitude = 21.0;
    private Double latitude = 22.0;

    private Integer estimatedTime;

    @ElementCollection(targetClass = SkillEnum.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "help_request_skills", joinColumns = @JoinColumn(name = "help_request_id"))
    @Column(name = "skill")
    private Set<SkillEnum> requiredSkills = new HashSet<>();

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @Enumerated(EnumType.STRING)
    private HelpRequestStatusEnum status = HelpRequestStatusEnum.PENDING;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;
}
