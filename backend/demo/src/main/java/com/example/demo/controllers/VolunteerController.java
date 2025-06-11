package com.example.demo.controllers;

import com.example.demo.dtos.VolunteerDTO;
import com.example.demo.enities.Volunteer;
import com.example.demo.services.VolunteerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {
    private final VolunteerService volunteerService;
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @GetMapping("")
    public ResponseEntity<List<VolunteerDTO>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.getAllVolunteer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDTO> getVolunteerById(@PathVariable Integer id) {
        return ResponseEntity.ok(volunteerService.getVolunteerById(id));
    }

    @PutMapping("/edit")
    public ResponseEntity<VolunteerDTO> updateVolunteer(@RequestBody VolunteerDTO volunteerDTO) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(volunteerDTO));
    }
}
