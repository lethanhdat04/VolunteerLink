package com.example.demo.services;

import com.example.demo.dtos.VolunteerDTO;
import com.example.demo.enities.Role;
import com.example.demo.enities.Volunteer;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.VolunteerRepository;
import com.example.demo.utils.DtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final DtoMapper dtoMapper;
    private final RoleRepository roleRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, DtoMapper dtoMapper, RoleRepository roleRepository) {
        this.volunteerRepository = volunteerRepository;
        this.dtoMapper = dtoMapper;
        this.roleRepository = roleRepository;
    }

    public List<VolunteerDTO> getAllVolunteer() {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return dtoMapper.mapList(volunteers, VolunteerDTO.class);
    }

    public VolunteerDTO getVolunteerById(Integer id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElse(null);
        return dtoMapper.map(volunteer, VolunteerDTO.class);
    }

//    public VolunteerDTO saveVolunteer(VolunteerDTO volunteerDTO) {
//        Volunteer volunteer = dtoMapper.map(volunteerDTO, Volunteer.class);
//
//        // Lấy role từ DB để tránh lỗi role.name = null
//        if (volunteerDTO.getRole() != null && volunteerDTO.getRole().getId() != null) {
//            Role role = roleRepository.findById(volunteerDTO.getRole().getId())
//                    .orElseThrow(() -> new RuntimeException("Role not found"));
//            volunteer.setRole(role);
//        }
//
//        volunteer = volunteerRepository.save(volunteer);
//        return dtoMapper.map(volunteer, VolunteerDTO.class);
//    }

    public VolunteerDTO updateVolunteer(VolunteerDTO volunteerDTO) {
        if (volunteerDTO.getId() == null) {
            throw new IllegalArgumentException("Volunteer id is null");
        }

        Volunteer volunteer = volunteerRepository.findById(volunteerDTO.getId()).orElseThrow(() -> new RuntimeException("Volunteer not found"));

        volunteer.setFullName(volunteerDTO.getFullName());
        volunteer.setRole(volunteerDTO.getRole());
        volunteer.setEmail(volunteerDTO.getEmail());
        volunteer.setAvailableHours(volunteerDTO.getAvailableHours());
        volunteer.setRating(volunteerDTO.getRating());
        volunteer.setSkills(volunteerDTO.getSkills());

        Volunteer updatedVolunteer = volunteerRepository.save(volunteer);
        return dtoMapper.map(updatedVolunteer, VolunteerDTO.class);
    }
}
