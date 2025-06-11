package com.example.demo.repositories;

import com.example.demo.enities.Event;
import com.example.demo.enums.EventStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByOrganizationId(Integer organizationId);
    List<Event> findByStatus(EventStatusEnum status);
    List<Event> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<Event> findByTitleContainingIgnoreCase(String title);
}
