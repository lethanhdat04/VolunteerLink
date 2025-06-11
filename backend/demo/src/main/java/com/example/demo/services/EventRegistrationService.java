package com.example.demo.services;

import com.example.demo.dtos.EventRegistrationDTO;
import com.example.demo.enities.Event;
import com.example.demo.enities.EventRegistration;
import com.example.demo.enities.Volunteer;
import com.example.demo.enums.RegistrationStatusEnum;
import com.example.demo.repositories.EventRegistrationRepository;
import com.example.demo.repositories.EventRepository;
import com.example.demo.repositories.VolunteerRepository;
import com.example.demo.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationService {
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;
    private final EventRegistrationRepository eventRegistrationRepository;
    private final DtoMapper dtoMapper;

    public EventRegistrationService(EventRepository eventRepository, VolunteerRepository volunteerRepository, EventRegistrationRepository eventRegistrationRepository, DtoMapper dtoMapper) {
        this.eventRepository = eventRepository;
        this.volunteerRepository = volunteerRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public EventRegistrationDTO registerVolunteerForEvent(Integer eventId, Integer volunteerId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"));

        Optional<EventRegistration> eventRegistrationOptional = eventRegistrationRepository.findByEventAndVolunteer(event, volunteer);
        if (eventRegistrationOptional.isPresent()) {
            throw new RuntimeException("Volunteer has already registered for this event");
        }

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setEvent(event);
        eventRegistration.setVolunteer(volunteer);
        eventRegistration.setRegistrationTime(LocalDateTime.now());
        eventRegistration.setStatus(RegistrationStatusEnum.REGISTERED);

        EventRegistration savedEntity = eventRegistrationRepository.save(eventRegistration);
        return dtoMapper.map(savedEntity, EventRegistrationDTO.class);
    }

    public EventRegistrationDTO getRegistrationById(Integer id) {
        EventRegistration eventRegistration = eventRegistrationRepository.findById(id).orElseThrow(() -> new RuntimeException("Registration not found"));
        return dtoMapper.map(eventRegistration, EventRegistrationDTO.class);
    }

    public List<EventRegistrationDTO> getRegistrationsForEvent(Integer eventId) {
        eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findAllByEventId(eventId);
        return dtoMapper.mapList(eventRegistrations, EventRegistrationDTO.class);
    }

    public List<EventRegistrationDTO> getRegistrationsForVolunteer(Integer volunteerId) {
        volunteerRepository.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"));
        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findAllByVolunteerId(volunteerId);
        return dtoMapper.mapList(eventRegistrations, EventRegistrationDTO.class);
    }

    @Transactional
    public EventRegistrationDTO updateRegistration(EventRegistrationDTO eventRegistrationDTO) {
        if (eventRegistrationDTO.getId() == null) {
            throw new IllegalArgumentException("Registration id is null");
        }
        EventRegistration eventRegistration = eventRegistrationRepository.findById(eventRegistrationDTO.getId()).orElseThrow(() -> new RuntimeException("Registration not found"));
        eventRegistration.setRegistrationTime(eventRegistrationDTO.getRegistrationDate());
        eventRegistration.setStatus(eventRegistrationDTO.getStatus());
//        eventRegistration.setReview(eventRegistrationDTO.getReview());
        eventRegistration.setRating(eventRegistrationDTO.getRating());
        eventRegistration.setVolunteer(volunteerRepository.findById(eventRegistrationDTO.getVolunteerId()).orElseThrow(() -> new RuntimeException("Volunteer not found")));
        eventRegistration.setEvent(eventRepository.findById(eventRegistrationDTO.getEventId()).orElseThrow(() -> new RuntimeException("Event not found")));

        EventRegistration updatedEventRegistration = eventRegistrationRepository.save(eventRegistration);
        return dtoMapper.map(updatedEventRegistration, EventRegistrationDTO.class);
    }

    @Transactional
    public EventRegistrationDTO approveRegistration(Integer id) {
        EventRegistration eventRegistration = eventRegistrationRepository.findById(id).orElseThrow(() -> new RuntimeException("Registration not found"));
        eventRegistration.setStatus(RegistrationStatusEnum.APPROVED);
        eventRegistration.setUpdateAt(LocalDateTime.now());
        EventRegistration updatedEventRegistration = eventRegistrationRepository.save(eventRegistration);
        return dtoMapper.map(updatedEventRegistration, EventRegistrationDTO.class);
    }

    @Transactional
    public EventRegistrationDTO rejectRegistration(Integer id) {
        EventRegistration eventRegistration = eventRegistrationRepository.findById(id).orElseThrow(() -> new RuntimeException("Registration not found"));
        eventRegistration.setStatus(RegistrationStatusEnum.REJECTED);
        eventRegistration.setUpdateAt(LocalDateTime.now());
        EventRegistration updatedEventRegistration = eventRegistrationRepository.save(eventRegistration);
        return dtoMapper.map(updatedEventRegistration, EventRegistrationDTO.class);
    }
}
