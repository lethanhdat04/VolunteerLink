package com.example.demo.repositories;

import com.example.demo.enities.HelpRequest;
import com.example.demo.enums.HelpRequestStatusEnum;
import com.example.demo.enums.PriorityEnum;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Integer> {
    List<HelpRequest> findByRecipientId(Integer recipientId);
    List<HelpRequest> findByOrganizationId(Integer organizationId);
    List<HelpRequest> findByStatus(HelpRequestStatusEnum status);
    List<HelpRequest> findByPriority(PriorityEnum priority);
    List<HelpRequest> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
