package com.example.demo.repositories;

import com.example.demo.enities.Organization;
import com.example.demo.enities.SupportArea;
import com.example.demo.enums.SkillEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findByType(String type);
    List<Organization> findBySupportAreasContaining(SupportArea supportArea);

    // Query method để tìm charity orgs có support area với skill cụ thể
    @Query("SELECT DISTINCT co FROM Organization co JOIN co.supportAreas sa WHERE :skill MEMBER OF sa.requiredSkills")
    List<Organization> findByRequiredSkill(@Param("skill") SkillEnum skill);
}
