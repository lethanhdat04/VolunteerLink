package com.example.demo.repositories;

import com.example.demo.enities.Event;
import com.example.demo.enities.EventRegistration;
import com.example.demo.enities.Volunteer;
import com.example.demo.enums.RegistrationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {
    Optional<EventRegistration> findByEventAndVolunteer(Event event, Volunteer volunteer);

    Optional<EventRegistration> findByEventIdAndVolunteerId(Integer eventId, Integer volunteerId);

    List<EventRegistration> findByStatus(RegistrationStatusEnum status);

    List<EventRegistration> findAllByEventId(Integer eventId);

    List<EventRegistration> findAllByVolunteerId(Integer volunteerId);
}
