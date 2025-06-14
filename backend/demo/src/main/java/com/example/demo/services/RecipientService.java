package com.example.demo.services;

import com.example.demo.dtos.RecipientDTO;
import com.example.demo.enities.Recipient;
import com.example.demo.repositories.RecipientRepository;
import com.example.demo.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipientService {
    private final RecipientRepository recipientRepository;
    private final DtoMapper dtoMapper;

    public RecipientService(RecipientRepository recipientRepository, DtoMapper dtoMapper) {
        this.recipientRepository = recipientRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public List<RecipientDTO> getAllRecipient() {
        return dtoMapper.mapList(recipientRepository.findAll(), RecipientDTO.class);
    }

    @Transactional
    public RecipientDTO getRecipientById(Integer id) {
        return dtoMapper.map(recipientRepository.findById(id).orElse(null), RecipientDTO.class);
    }

    @Transactional
    public RecipientDTO updateRecipient(Integer id, RecipientDTO recipientDTO) {
        Recipient recipient = recipientRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipient not found"));
        recipient.setUpdateAt(LocalDateTime.now());
        //recipient.setAddress(recipientDTO.getAddress());
        recipient.setLatitude(recipientDTO.getLatitude());
        recipient.setLongitude(recipientDTO.getLongitude());
        recipient.setFullName(recipientDTO.getFullName());
        recipient.setEmail(recipientDTO.getEmail());
        recipient.setPhoneNumber(recipientDTO.getPhoneNumber());
        recipient.setStatus(recipientDTO.getStatus());

        return dtoMapper.map(recipientRepository.save(recipient), RecipientDTO.class);
    }


}
