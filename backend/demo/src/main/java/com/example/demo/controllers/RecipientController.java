package com.example.demo.controllers;

import com.example.demo.dtos.RecipientDTO;
import com.example.demo.services.RecipientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipients")
public class RecipientController {
    private final RecipientService recipientService;
    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @GetMapping("")
    public ResponseEntity<List<RecipientDTO>> getAllRecipient() {
        return ResponseEntity.ok(recipientService.getAllRecipient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipientDTO> getRecipientById(@PathVariable Integer id) {
        return ResponseEntity.ok(recipientService.getRecipientById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<RecipientDTO> updateRecipient(@RequestBody RecipientDTO recipientDTO) {
        return ResponseEntity.ok(recipientService.updateRecipient(recipientDTO));
    }
}
