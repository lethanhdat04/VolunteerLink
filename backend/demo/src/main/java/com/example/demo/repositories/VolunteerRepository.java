package com.example.demo.repositories;

import com.example.demo.enities.Volunteer;
import com.example.demo.enums.SkillEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {

    // Query method để tìm volunteer có skill cụ thể
    @Query("SELECT v FROM Volunteer v WHERE :skill MEMBER OF v.skills")
    List<Volunteer> findBySkillsContaining(@Param("skill")SkillEnum skill);

    // Query method để tìm volunteers có bất kỳ skill nào trong danh sách
    @Query("SELECT v FROM Volunteer v WHERE EXISTS (SELECT s FROM v.skills s WHERE s IN :skills)")
    List<Volunteer> findBySkillsIn(@Param("skills") Set<SkillEnum> skills);
}
