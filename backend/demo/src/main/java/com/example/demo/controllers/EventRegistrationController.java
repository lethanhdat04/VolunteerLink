package com.example.demo.controllers;

import com.example.demo.dtos.EventRegistrationDTO;
import com.example.demo.enities.EventRegistration;
import com.example.demo.services.EventRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event-registration")
public class EventRegistrationController {
    private final EventRegistrationService eventRegistrationService;
    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/events/{eventId}/volunteers/{volunteerId}")
    public ResponseEntity<?> registerVolunteer(@PathVariable Integer eventId, @PathVariable Integer volunteerId) {
        try {
            EventRegistrationDTO eventRegistration = eventRegistrationService.registerVolunteerForEvent(eventId, volunteerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventRegistration);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventRegistrationById(@PathVariable Integer id) {
        try {
            EventRegistrationDTO eventRegistration = eventRegistrationService.getRegistrationById(id);
            return ResponseEntity.ok(eventRegistration);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Event registration not found");
        }
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<?> getRegistrationForEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsForEvent(eventId));
    }

    @GetMapping("/volunteers/{volunteerId}")
    public ResponseEntity<?> getRegistrationForVolunteer(@PathVariable Integer volunteerId) {
        return ResponseEntity.ok(eventRegistrationService.getRegistrationsForVolunteer(volunteerId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRegistration(@RequestBody EventRegistrationDTO eventRegistrationDTO) {
        return ResponseEntity.ok(eventRegistrationService.updateRegistration(eventRegistrationDTO));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approveRegistration(@PathVariable Integer id) {
        return ResponseEntity.ok(eventRegistrationService.approveRegistration(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> rejectRegistration(@PathVariable Integer id) {
        return ResponseEntity.ok(eventRegistrationService.rejectRegistration(id));
    }

    @PatchMapping("{id}/pending")
    public ResponseEntity<?> pendingRegistration(@PathVariable Integer id) {
        return ResponseEntity.ok(eventRegistrationService.pendingRegistration(id));
    }
}
