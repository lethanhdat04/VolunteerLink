package com.example.demo.services;

import com.example.demo.dtos.CreateHelpRequestDTO;
import com.example.demo.dtos.HelpRequestDTO;
import com.example.demo.dtos.RecipientDTO;
import com.example.demo.enities.HelpRequest;
import com.example.demo.enities.Recipient;
import com.example.demo.enums.HelpRequestStatusEnum;
import com.example.demo.enums.PriorityEnum;
import com.example.demo.repositories.HelpRequestRepository;
import com.example.demo.repositories.OrganizationRepository;
import com.example.demo.repositories.RecipientRepository;
import com.example.demo.repositories.VolunteerRepository;
import com.example.demo.utils.DtoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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

    public List<HelpRequestDTO> getAllHelpRequest() {
        List<HelpRequest> helpRequests = helpRequestRepository.findAll();
        List<HelpRequestDTO> helpRequestDTOS = new ArrayList<>();
        for (HelpRequest helpRequest : helpRequests) {
            helpRequestDTOS.add(new HelpRequestDTO(helpRequest));
        }
        return helpRequestDTOS;
    }

    public HelpRequestDTO getHelpRequestById(Integer id) {
        HelpRequest helpRequest = helpRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Help request not found"));
        return new HelpRequestDTO(helpRequest);
    }

    public HelpRequestDTO createRequest(CreateHelpRequestDTO requestDTO) {
        HelpRequest helpRequest = dtoMapper.map(requestDTO, HelpRequest.class);
        Recipient recipient = recipientRepository.findById(requestDTO.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found"));
        helpRequest.setTitle(requestDTO.getTitle());
        helpRequest.setRecipient(recipient);
        helpRequest.setStatus(HelpRequestStatusEnum.PENDING);
        helpRequest.setCreateAt(LocalDateTime.now());
        helpRequest.setPriority(requestDTO.getPriority());
        if (requestDTO.getRequiredSkills() != null) {
            helpRequest.setRequiredSkills(new HashSet<>(requestDTO.getRequiredSkills()));
        } else {
            helpRequest.setRequiredSkills(new HashSet<>());
        }
        HelpRequest updated = helpRequestRepository.save(helpRequest);
        return new HelpRequestDTO(updated);
    }

    public HelpRequestDTO updateRequest(Integer id, HelpRequestDTO helpRequestDTO) {
        HelpRequest helpRequest = helpRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Help request not found"));
        helpRequest.setUpdateAt(LocalDateTime.now());
        helpRequest.setDescription(helpRequestDTO.getDescription());
        helpRequest.setEndTime(helpRequestDTO.getEndTime());
        helpRequest.setLocation(helpRequestDTO.getLocation());
        helpRequest.setPriority(helpRequestDTO.getPriority());
        helpRequest.setStartTime(helpRequestDTO.getStartTime());
        helpRequest.setTitle(helpRequestDTO.getTitle());
        HelpRequest updated = helpRequestRepository.save(helpRequest);
        return new HelpRequestDTO(updated);
    }

    public List<HelpRequestDTO> findByRecipientId(Integer recipientId) {
        List<HelpRequest> helpRequests = helpRequestRepository.findByRecipientId(recipientId);
        List<HelpRequestDTO> helpRequestDTOS = new ArrayList<>();
        for (HelpRequest helpRequest : helpRequests) {
            helpRequestDTOS.add(new HelpRequestDTO(helpRequest));
        }
        return helpRequestDTOS;
    }

    public List<HelpRequestDTO> findByStatus(HelpRequestStatusEnum status) {
        List<HelpRequest> helpRequests = helpRequestRepository.findByStatus(status);
        List<HelpRequestDTO> helpRequestDTOS = new ArrayList<>();
        for (HelpRequest helpRequest : helpRequests) {
            helpRequestDTOS.add(new HelpRequestDTO(helpRequest));
        }
        return helpRequestDTOS;
    }

    public List<HelpRequestDTO> findByPriority(PriorityEnum priority) {
        List<HelpRequest> helpRequests = helpRequestRepository.findByPriority(priority);
        List<HelpRequestDTO> helpRequestDTOS = new ArrayList<>();
        for (HelpRequest helpRequest : helpRequests) {
            helpRequestDTOS.add(new HelpRequestDTO(helpRequest));
        }
        return helpRequestDTOS;
    }

    public HelpRequestDTO approveRequest(Integer requestId) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Help request not found"));
        helpRequest.setStatus(HelpRequestStatusEnum.ACCEPTED);
        helpRequest.setUpdateAt(LocalDateTime.now());
        return new HelpRequestDTO(helpRequestRepository.save(helpRequest));
    }

    public HelpRequestDTO rejectRequest(Integer requestId) {
        HelpRequest helpRequest = helpRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Help request not found"));
        helpRequest.setStatus(HelpRequestStatusEnum.REJECTED);
        helpRequest.setUpdateAt(LocalDateTime.now());
        return new HelpRequestDTO(helpRequestRepository.save(helpRequest));
    }

    public void deleteRequest(Integer requestId) {
        helpRequestRepository.deleteById(requestId);
    }
}
