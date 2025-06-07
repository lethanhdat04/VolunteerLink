package com.example.demo.services;

import com.example.demo.dtos.HelpRequestDTO;
import com.example.demo.enities.HelpRequest;
import com.example.demo.enums.HelpRequestStatusEnum;
import com.example.demo.repositories.HelpRequestRepository;
import com.example.demo.repositories.OrganizationRepository;
import com.example.demo.repositories.RecipientRepository;
import com.example.demo.repositories.VolunteerRepository;
import com.example.demo.utils.DtoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HelpRequestService {
    private final HelpRequestRepository helpRequestRepository;
    private final RecipientRepository recipientRepository;
    private final VolunteerRepository volunteerRepository;
    private final OrganizationRepository organizationRepository;
    private final DtoMapper dtoMapper;

    public HelpRequestService(HelpRequestRepository helpRequestRepository, RecipientRepository recipientRepository, VolunteerRepository volunteerRepository, OrganizationRepository organizationRepository, DtoMapper dtoMapper) {
        this.helpRequestRepository = helpRequestRepository;
        this.recipientRepository = recipientRepository;
        this.volunteerRepository = volunteerRepository;
        this.organizationRepository = organizationRepository;
        this.dtoMapper = dtoMapper;
    }

    public HelpRequestDTO createRequest(HelpRequestDTO helpRequestDTO) {
        HelpRequest helpRequest = dtoMapper.map(helpRequestDTO, HelpRequest.class);
        helpRequest.setRecipient(recipientRepository.findById(helpRequestDTO.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found")));
        helpRequest.setVolunteer(volunteerRepository.findById(helpRequestDTO.getVolunteerId()).orElseThrow(() -> new RuntimeException("Volunteer not found")));
        helpRequest.setOrganization(organizationRepository.findById(helpRequestDTO.getOrganizationId()).orElseThrow(() -> new RuntimeException("Organization not found")));
        HelpRequest updated = helpRequestRepository.save(helpRequest);
        return dtoMapper.map(updated, HelpRequestDTO.class);
    }

    public List<HelpRequestDTO> findByRecipientId(Integer recipientId) {
        List<HelpRequest> helpRequests = helpRequestRepository.findByRecipientId(recipientId);
        return dtoMapper.mapList(helpRequests, HelpRequestDTO.class);
    }

    public List<HelpRequestDTO> findByStatus(HelpRequestStatusEnum status) {
        List<HelpRequest> helpRequests = helpRequestRepository.findByStatus(status);
        return dtoMapper.mapList(helpRequests, HelpRequestDTO.class);
    }

    public HelpRequestDTO approveRequest(Integer requestId) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Help request not found"));
        helpRequest.setStatus(HelpRequestStatusEnum.ACCEPTED);
        helpRequest.setUpdateAt(LocalDateTime.now());
        return dtoMapper.map(helpRequestRepository.save(helpRequest), HelpRequestDTO.class);
    }

    public HelpRequestDTO rejectRequest(Integer requestId) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Help request not found"));
        helpRequest.setStatus(HelpRequestStatusEnum.REJECTED);
        helpRequest.setUpdateAt(LocalDateTime.now());
        return dtoMapper.map(helpRequestRepository.save(helpRequest), HelpRequestDTO.class);
    }

}
