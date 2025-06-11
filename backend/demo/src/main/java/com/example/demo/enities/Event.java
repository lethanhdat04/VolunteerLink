package com.example.demo.enities;

import com.example.demo.enums.EventStatusEnum;
import com.example.demo.enums.SkillEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

//    @ElementCollection(targetClass = SkillEnum.class, fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "event_require_skills", joinColumns = @JoinColumn(name = "event_id"))
//    private Set<SkillEnum> requiredSkills = new HashSet<>();

    private Integer maxParticipants;

    private Integer participants;

    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private Set<EventRegistration> getRegistrations = new HashSet<>();
}
