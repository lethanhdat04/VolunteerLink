package com.example.demo.controllers;

import com.example.demo.dtos.CreateHelpRequestDTO;
import com.example.demo.dtos.HelpRequestDTO;
import com.example.demo.enities.HelpRequest;
import com.example.demo.enums.HelpRequestStatusEnum;
import com.example.demo.enums.PriorityEnum;
import com.example.demo.services.HelpRequestService;
import com.example.demo.utils.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help-requests")
public class HelpRequestController {
    private final HelpRequestService helpRequestService;

    public HelpRequestController(HelpRequestService helpRequestService) {
        this.helpRequestService = helpRequestService;
    }

    @GetMapping
    public ResponseEntity<List<HelpRequestDTO>> getAllRequests() {
        return ResponseEntity.ok(helpRequestService.getAllHelpRequest());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HelpRequestDTO> getRequestById(@PathVariable Integer id) {
        return ResponseEntity.ok(helpRequestService.getHelpRequestById(id));
    }

    @PostMapping
    public ResponseEntity<HelpRequestDTO> createRequest(@RequestBody CreateHelpRequestDTO helpRequestDTO) {
        return ResponseEntity.ok(helpRequestService.createRequest(helpRequestDTO));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<HelpRequestDTO> approveRequest(@PathVariable Integer id) {
        return ResponseEntity.ok(helpRequestService.approveRequest(id));
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<HelpRequestDTO> rejectRequest(@PathVariable Integer id) {
        return ResponseEntity.ok(helpRequestService.rejectRequest(id));
    }


    @GetMapping("/recipient/{id}")
    public ResponseEntity<List<HelpRequestDTO>> getRequestsByRecipientId(@PathVariable Integer id) {
        return ResponseEntity.ok(helpRequestService.findByRecipientId(id));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<HelpRequestDTO>> getRequestByPriority(@PathVariable PriorityEnum priority) {
        return ResponseEntity.ok(helpRequestService.findByPriority(priority));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<HelpRequestDTO>> getRequestByStatus(@PathVariable HelpRequestStatusEnum status) {
        return ResponseEntity.ok(helpRequestService.findByStatus(status));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<HelpRequestDTO> updateRequest(@PathVariable Integer id, @RequestBody HelpRequestDTO helpRequestDTO) {
        return ResponseEntity.ok(helpRequestService.updateRequest(id, helpRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
        helpRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
