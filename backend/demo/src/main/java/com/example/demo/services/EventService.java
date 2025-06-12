package com.example.demo.services;

import com.example.demo.dtos.EventDTO;
import com.example.demo.enities.Event;
import com.example.demo.enities.Organization;
import com.example.demo.repositories.EventRepository;
import com.example.demo.repositories.OrganizationRepository;
import com.example.demo.repositories.VolunteerRepository;
import com.example.demo.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final DtoMapper dtoMapper;
    private final OrganizationRepository organizationRepository;
    private final VolunteerRepository volunteerRepository;

    public EventService(EventRepository eventRepository, DtoMapper dtoMapper, OrganizationRepository organizationRepository, VolunteerRepository volunteerRepository) {
        this.eventRepository = eventRepository;
        this.dtoMapper = dtoMapper;
        this.organizationRepository = organizationRepository;
        this.volunteerRepository = volunteerRepository;
    }

    @Transactional
    public List<EventDTO> getAllEvent() {
        List<Event> events = eventRepository.findAll();
        return dtoMapper.mapList(events, EventDTO.class);
    }

    @Transactional
    public EventDTO getEventById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return dtoMapper.map(event, EventDTO.class);
    }

    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        event.setStatus(eventDTO.getStatus());
        event.setParticipants(eventDTO.getParticipants());


        // Load lại Organization từ DB
        Organization org = organizationRepository.findById(eventDTO.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        event.setOrganization(org); // tránh detached

        event = eventRepository.save(event);

        return dtoMapper.map(event, EventDTO.class);
    }


    @Transactional
    public EventDTO updateEvent(EventDTO eventDTO) {
        if (eventDTO.getId() == null) {
            throw new IllegalArgumentException("Event id is null");
        }
        Event event = eventRepository.findById(eventDTO.getId()).orElseThrow(() -> new RuntimeException("Event not found"));
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setParticipants(eventDTO.getParticipants());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        event.setTitle(eventDTO.getTitle());
        event.setStatus(eventDTO.getStatus());

        Event updatedEvent = eventRepository.save(event);
        return dtoMapper.map(updatedEvent, EventDTO.class);
    }

    @Transactional
    public List<EventDTO> findByOrganizationId(Integer organizationId) {
        List<Event> events = eventRepository.findByOrganizationId(organizationId);
        return dtoMapper.mapList(events, EventDTO.class);
    }

    @Transactional
    public List<EventDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findByStartTimeBetween(startDate, endDate);
        return dtoMapper.mapList(events, EventDTO.class);
    }

    @Transactional
    public ResponseEntity<Void> deleteEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        eventRepository.delete(event);
        return null;
    }
}
