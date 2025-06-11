package com.example.demo.controllers;

import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.services.VolunteerMatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping()
public class VolunteerMatchingController {
    private final VolunteerMatchingService volunteerMatchingService;
    public VolunteerMatchingController(VolunteerMatchingService volunteerMatchingService) {
        this.volunteerMatchingService = volunteerMatchingService;
    }

    @GetMapping("/help-requests/{id}/find-volunteers")
    public ResponseEntity<List<MatchResultDTO>> findVolunteers(@PathVariable Integer id) {
        log.info("Tìm tình nguyện viên cho yêu cầu");
        List<MatchResultDTO> matches = volunteerMatchingService.findMatchingVolunteers(id);
        return ResponseEntity.ok(matches);
    }
}
