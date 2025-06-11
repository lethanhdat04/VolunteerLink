package com.example.demo.controllers;

import com.example.demo.dtos.VolunteerDTO;
import com.example.demo.enities.Volunteer;
import com.example.demo.enums.SkillEnum;
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

    @GetMapping("/skill/{skill}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteersBySkill(@PathVariable SkillEnum skill) {
        return ResponseEntity.ok(volunteerService.getVolunteersBySkill(skill));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<VolunteerDTO> updateVolunteer(@PathVariable Integer id, @RequestBody VolunteerDTO volunteerDTO) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(id, volunteerDTO));
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Integer id) {
        volunteerService.deleteVolunteerById(id);
        return ResponseEntity.noContent().build();
    }
}
