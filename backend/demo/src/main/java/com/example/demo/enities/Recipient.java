package com.example.demo.enities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Recipient extends User {
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<HelpRequest> helpRequests = new ArrayList<>();
}
