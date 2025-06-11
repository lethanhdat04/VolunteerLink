package com.example.demo.services;

import com.example.demo.dtos.HelpRequestDTO;
import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.dtos.MatchingCriteriaDTO;
import com.example.demo.dtos.VolunteerDTO;
import com.example.demo.enities.HelpRequest;
import com.example.demo.enities.Volunteer;
import com.example.demo.enums.SkillEnum;
import com.example.demo.repositories.HelpRequestRepository;
import com.example.demo.repositories.VolunteerRepository;
import com.example.demo.utils.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VolunteerMatchingService {
    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double MAX_SCORE = 100.0;

    // Trọng số cho các tiêu chí matching
    private static final double SKILL_WEIGHT = 0.4;
    private static final double AVAILABILITY_WEIGHT = 0.25;
    private static final double DISTANCE_WEIGHT = 0.2;
    private static final double RATING_WEIGHT = 0.15;

    private final HelpRequestRepository helpRequestRepository;
    private final VolunteerRepository volunteerRepository;
    private final DtoMapper dtoMapper;

    public VolunteerMatchingService(HelpRequestRepository helpRequestRepository, VolunteerRepository volunteerRepository, DtoMapper dtoMapper) {
        this.helpRequestRepository = helpRequestRepository;
        this.volunteerRepository = volunteerRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<MatchResultDTO> findMatchingVolunteers(Integer id) {
        HelpRequest helpRequest = helpRequestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Help Request"));
        HelpRequestDTO helpRequestDTO = new HelpRequestDTO(helpRequest);
        List<Volunteer> volunteers = volunteerRepository.findAll();
        List<VolunteerDTO> availableVolunteers = dtoMapper.mapList(volunteers, VolunteerDTO.class);
        MatchingCriteriaDTO criteria = getDefaultCriteria();
        log.info("Tìm tình nguyện viên cho yêu cầu ID: {}", helpRequest.getId());
        return availableVolunteers.stream()
                .filter(volunteerDTO -> isVolunteerEligible(volunteerDTO, helpRequestDTO, criteria))
                .map(volunteerDTO -> calculateMatch(volunteerDTO, helpRequestDTO))
                .sorted((m1, m2) -> Double.compare(m2.getMatchScore(), m1.getMatchScore()))
                .limit(criteria.getMaxResults() != null ? criteria.getMaxResults() : 10)
                .collect(Collectors.toList());
    }

    private MatchingCriteriaDTO getDefaultCriteria() {
        MatchingCriteriaDTO criteria = new MatchingCriteriaDTO();
        criteria.setMinRating(3);       // ví dụ: yêu cầu đánh giá tối thiểu
        criteria.setMaxDistance(30.0);    // ví dụ: bán kính 30km
        criteria.setMaxResults(10);       // giới hạn kết quả trả về
        return criteria;
    }

    public boolean isVolunteerEligible(VolunteerDTO volunteerDTO, HelpRequestDTO helpRequestDTO, MatchingCriteriaDTO criteria) {
//        if (volunteerDTO.getStatus().toString().equals("INACTIVE")) {
//            return false;
//        }

//        if (criteria.getMinRating() != null && volunteerDTO.getRating() < criteria.getMinRating()) {
//            return false;
//        }

//        if (criteria.getMaxDistance() != null) {
//            double distance = calculateDistance(
//                    volunteerDTO.getLongitude(), volunteerDTO.getLatitude(),
//                    helpRequestDTO.getLongitude(), volunteerDTO.getLatitude()
//            );
//            if (distance > criteria.getMaxDistance()) {
//                return false;
//            }
//        }

        return true;

        // return helpRequestDTO.getEstimatedTime() == null || volunteerDTO.getAvailableHours() >= helpRequestDTO.getEstimatedTime();
    }

    private MatchResultDTO calculateMatch(VolunteerDTO volunteerDTO, HelpRequestDTO helpRequestDTO) {

        double skillScore = calculateSkillScore(volunteerDTO, helpRequestDTO);
        double availabilityScore = calculateAvailabilityScore(volunteerDTO, helpRequestDTO);
        double distanceScore = calculateDistanceScore(volunteerDTO, helpRequestDTO);
        double ratingScore = calculateRatingScore(volunteerDTO);

        double totalScore = (skillScore * SKILL_WEIGHT) +
                            (availabilityScore * AVAILABILITY_WEIGHT) +
                            (distanceScore * DISTANCE_WEIGHT) +
                            (ratingScore * RATING_WEIGHT);

        MatchResultDTO result = new MatchResultDTO();
        result.setVolunteerId(volunteerDTO.getId());
        result.setVolunteerName(volunteerDTO.getFullName());
        result.setRequestId(helpRequestDTO.getId());
        result.setRequestTitle(helpRequestDTO.getTitle());
        result.setMatchScore(Math.round(totalScore * 100.0) / 100.0);
        result.setDistance(calculateDistance(volunteerDTO.getLatitude(), volunteerDTO.getLongitude(), helpRequestDTO.getLatitude(), helpRequestDTO.getLongitude()));
        result.setMatchReason(generateMatchReason(skillScore, availabilityScore, distanceScore, ratingScore));
        return result;
    }

    private double calculateSkillScore(VolunteerDTO volunteerDTO, HelpRequestDTO helpRequestDTO) {
        Set<SkillEnum> requiredSkills = helpRequestDTO.getRequiredSkills();
        Set<SkillEnum> volunteerSkills = volunteerDTO.getSkills();
        if (requiredSkills == null || requiredSkills.isEmpty()) {
            return 10.0;
        }
        if (volunteerSkills == null || volunteerSkills.isEmpty()) {
            return 0.0;
        }

        long exactMatches = requiredSkills.stream()
                .filter(volunteerSkills::contains)
                .count();

        double relatedScore = calculateRelatedSkillScore(volunteerSkills, requiredSkills);

        double exactScore = ((double) exactMatches / requiredSkills.size());

        return Math.min(MAX_SCORE, exactScore + relatedScore);
    }

    private double calculateRelatedSkillScore(Set<SkillEnum> volunteerSkills, Set<SkillEnum> requiredSkills) {
        Map<SkillEnum, Set<SkillEnum>> relatedSkills = Map.of(
                SkillEnum.EDUCATION, Set.of(SkillEnum.SOCIAL_WORK, SkillEnum.COMMUNICATION),
                SkillEnum.HEALTHCARE, Set.of(SkillEnum.EMERGENCY_RELIEF, SkillEnum.ELDERLY_CARE),
                SkillEnum.EMERGENCY_RELIEF, Set.of(SkillEnum.HEALTHCARE, SkillEnum.LOGISTICS),
                SkillEnum.SOCIAL_WORK, Set.of(SkillEnum.EDUCATION, SkillEnum.ELDERLY_CARE),
                SkillEnum.EVENT_ORGANIZATION, Set.of(SkillEnum.COMMUNICATION, SkillEnum.LOGISTICS)
        );

        Set<SkillEnum> matchedRelatedSkills = new HashSet<>();

        for (SkillEnum required : requiredSkills) {
            Set<SkillEnum> related = relatedSkills.getOrDefault(required, Collections.emptySet());
            for (SkillEnum vs : volunteerSkills) {
                if (related.contains(vs)) {
                    matchedRelatedSkills.add(vs);
                }
            }
        }

        double ratio = (double) matchedRelatedSkills.size() / (double) relatedSkills.size();

        return MAX_SCORE * ratio * SKILL_WEIGHT;
    }

    private double calculateAvailabilityScore(VolunteerDTO volunteerDTO, HelpRequestDTO helpRequestDTO) {
        if (helpRequestDTO.getEstimatedTime() == null) {
            return MAX_SCORE;
        }

        double ratio = (double) volunteerDTO.getAvailableHours() / (double) helpRequestDTO.getEstimatedTime();

        if (ratio >= 2.0) return MAX_SCORE;
        if (ratio >= 1.5) return MAX_SCORE * 0.9;
        if (ratio >= 1.0) return MAX_SCORE * 0.8;
        if (ratio >= 0.8) return MAX_SCORE * 0.6;

        return MAX_SCORE * 0.3;
    }

    private double calculateDistanceScore(VolunteerDTO volunteerDTO, HelpRequestDTO helpRequestDTO) {
        double distance = calculateDistance(
                volunteerDTO.getLatitude(), volunteerDTO.getLongitude(),
                helpRequestDTO.getLatitude(), helpRequestDTO.getLongitude()
        );

        if (distance <= 5) return MAX_SCORE;
        if (distance <= 10) return MAX_SCORE * 0.8;
        if (distance <= 20) return MAX_SCORE * 0.6;
        if (distance <= 50) return MAX_SCORE * 0.4;

        return MAX_SCORE * 0.2;
    }

    private double calculateRatingScore(VolunteerDTO volunteer) {
        if (volunteer.getRating() == null) {
            return MAX_SCORE * 0.5; // Điểm trung bình cho người mới
        }

        return (volunteer.getRating() / 5.0) * MAX_SCORE;
    }

    private double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            return Double.MAX_VALUE;
        }

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private String generateMatchReason(double skillScore, double availabilityScore,
                                       double distanceScore, double ratingScore) {
        List<String> reasons = new ArrayList<>();

        if (skillScore >= MAX_SCORE * 0.8) {
            reasons.add("Kỹ năng phù hợp cao");
        }
        if (availabilityScore >= MAX_SCORE * 0.8) {
            reasons.add("Thời gian rảnh đủ");
        }
        if (distanceScore >= MAX_SCORE * 0.8) {
            reasons.add("Khoảng cách gần");
        }
        if (ratingScore >= MAX_SCORE * 0.8) {
            reasons.add("Đánh giá tốt");
        }

        return reasons.isEmpty() ? "Phù hợp cơ bản" : String.join(", ", reasons);
    }


}
