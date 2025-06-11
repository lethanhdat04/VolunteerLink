//package com.example.demo.enities;
//
//import com.example.demo.enums.SkillEnum;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Data
//@Table(name = "support_areas")
//public class SupportArea {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String name;
//
//    // ElementCollection for Skills using Enum (thay vì Many-to-Many)
//    @ElementCollection(targetClass = SkillEnum.class, fetch = FetchType.EAGER)
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "support_area_skills", joinColumns = @JoinColumn(name = "support_area_id"))
//    @Column(name = "skill")
//    private Set<SkillEnum> requiredSkills = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "charity_orgs_support_areas",
//            joinColumns = @JoinColumn(name = "support_area_id"),
//            inverseJoinColumns = @JoinColumn(name = "charity_org_id")
//    )
//    private Set<Organization> organizations = new HashSet<>();
//}
