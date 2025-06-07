package com.example.demo.controllers;

import com.example.demo.dtos.EventDTO;
import com.example.demo.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/organization/{id}")
    public ResponseEntity<List<EventDTO>> getEventsByOrganizationId(@PathVariable Integer id) {
        return ResponseEntity.ok(eventService.findByOrganizationId(id));
    }

    @PostMapping("/create")
    public EventDTO saveEvent(@RequestBody EventDTO eventDTO) {
        return eventService.saveEvent(eventDTO);
    }

    @PutMapping("/update")
    public EventDTO updateEvent(@RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(eventDTO);
    }

}
