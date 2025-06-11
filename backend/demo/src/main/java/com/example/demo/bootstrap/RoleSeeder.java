package com.example.demo.bootstrap;

import com.example.demo.enities.Role;
import com.example.demo.enums.RoleEnum;
import com.example.demo.repositories.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] {
                RoleEnum.ADMIN,
                RoleEnum.ORGANIZATION,
                RoleEnum.VOLUNTEER,
                RoleEnum.RECIPIENT
        };

        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.ORGANIZATION, "Organization role",
                RoleEnum.RECIPIENT, "Recipient role",
                RoleEnum.VOLUNTEER, "Volunteer role"
        );

        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}
