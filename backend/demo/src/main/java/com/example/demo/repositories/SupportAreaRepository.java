//package com.example.demo.repositories;
//
//import com.example.demo.enities.SupportArea;
//import com.example.demo.enums.SkillEnum;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Repository
//public interface SupportAreaRepository extends JpaRepository<SupportArea, Integer> {
//    Optional<SupportArea> findByName(String name);
//
//    // Query method để tìm support areas có skill cụ thể
//    @Query("SELECT sa FROM SupportArea sa WHERE :skill MEMBER OF sa.requiredSkills")
//    List<SupportArea> findByRequiredSkillsContaining(@Param("skill")SkillEnum skill);
//
//
//    // Query method để tìm support areas có bất kỳ skill nào trong danh sách
//    @Query("SELECT sa FROM SupportArea sa WHERE EXISTS (SELECT s FROM sa.requiredSkills s WHERE s IN :skills)")
//    List<SupportArea> findByRequiredSkillsIn(@Param("skills") Set<SkillEnum> skills);
//
//}
