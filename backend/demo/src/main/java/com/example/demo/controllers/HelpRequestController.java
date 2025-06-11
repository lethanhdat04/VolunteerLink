package com.example.demo.controllers;

import com.example.demo.dtos.HelpRequestDTO;
import com.example.demo.enities.HelpRequest;
import com.example.demo.services.HelpRequestService;
import com.example.demo.utils.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help-requests")
public class HelpRequestController {
    private final HelpRequestService helpRequestService;
    private final DtoMapper dtoMapper;

    public HelpRequestController(HelpRequestService helpRequestService, DtoMapper dtoMapper) {
        this.helpRequestService = helpRequestService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<HelpRequestDTO> createRequest(@RequestBody HelpRequestDTO helpRequestDTO) {
        return ResponseEntity.ok(helpRequestService.createRequest(helpRequestDTO));
    }

    @GetMapping("/recipient/{id}")
    public ResponseEntity<List<HelpRequestDTO>> getRequestsByRecipientId(@PathVariable Integer id) {
        return ResponseEntity.ok(helpRequestService.findByRecipientId(id));
    }
}
