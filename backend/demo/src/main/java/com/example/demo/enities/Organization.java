package com.example.demo.enities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Organization extends User {
    private String description;

    private String type;

    private String website;

    @ManyToMany(mappedBy = "organizations")
    private Set<SupportArea> supportAreas = new HashSet<>();

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
    private Set<Event> events;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<HelpRequest> helpRequests = new ArrayList<>();
}
