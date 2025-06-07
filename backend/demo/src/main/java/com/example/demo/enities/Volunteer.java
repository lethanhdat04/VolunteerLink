package com.example.demo.enities;

import com.example.demo.enums.SkillEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Volunteer extends User {
    @ElementCollection(targetClass = SkillEnum.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "volunteer_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private Set<SkillEnum> skills = new HashSet<>();

    private Integer availableHours;

    private Float rating;

    @OneToMany(mappedBy = "volunteer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<EventRegistration> eventRegistrations = new HashSet<>();
}
