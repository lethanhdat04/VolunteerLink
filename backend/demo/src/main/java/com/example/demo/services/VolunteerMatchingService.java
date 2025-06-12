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
import jakarta.transaction.Transactional;
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

    @Transactional
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

//    public List<MatchResultDTO> findMatchingRequests(Integer id) {
//        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Volunteer not found"));
//        List<HelpRequest> helpRequests = helpRequestRepository.findAll();
//        List<HelpRequestDTO> helpRequestDTOS = new ArrayList<>();
//        for (HelpRequest helpRequest : helpRequests) {
//            helpRequestDTOS.add(new HelpRequestDTO(helpRequest));
//        }
//
//    }

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
        result.setMatchReason(generateMatchReason());
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
        // Định nghĩa các skill liên quan dựa trên logic thực tế
        Map<SkillEnum, Set<SkillEnum>> relatedSkills = new HashMap<>();

        // Nhóm Healthcare & Care
        relatedSkills.put(SkillEnum.HEALTHCARE, Set.of(SkillEnum.FIRST_AID, SkillEnum.ELDERLY_CARE, SkillEnum.COUNSELING, SkillEnum.EMERGENCY_RELIEF));
        relatedSkills.put(SkillEnum.FIRST_AID, Set.of(SkillEnum.HEALTHCARE, SkillEnum.EMERGENCY_RELIEF, SkillEnum.DISASTER_RELIEF));
        relatedSkills.put(SkillEnum.ELDERLY_CARE, Set.of(SkillEnum.HEALTHCARE, SkillEnum.COUNSELING, SkillEnum.CHILDCARE));
        relatedSkills.put(SkillEnum.CHILDCARE, Set.of(SkillEnum.EDUCATION, SkillEnum.TEACHING, SkillEnum.ELDERLY_CARE));
        relatedSkills.put(SkillEnum.COUNSELING, Set.of(SkillEnum.HEALTHCARE, SkillEnum.SOCIAL_WORK, SkillEnum.ELDERLY_CARE));

        // Nhóm Education & Communication
        relatedSkills.put(SkillEnum.EDUCATION, Set.of(SkillEnum.TEACHING, SkillEnum.COMMUNICATION, SkillEnum.CHILDCARE));
        relatedSkills.put(SkillEnum.TEACHING, Set.of(SkillEnum.EDUCATION, SkillEnum.COMMUNICATION, SkillEnum.CHILDCARE));
        relatedSkills.put(SkillEnum.COMMUNICATION, Set.of(SkillEnum.TEACHING, SkillEnum.COMMUNITY_OUTREACH, SkillEnum.SOCIAL_WORK, SkillEnum.TRANSLATION));
        relatedSkills.put(SkillEnum.TRANSLATION, Set.of(SkillEnum.COMMUNICATION, SkillEnum.TEACHING, SkillEnum.COMMUNITY_OUTREACH));

        // Nhóm Emergency & Relief
        relatedSkills.put(SkillEnum.EMERGENCY_RELIEF, Set.of(SkillEnum.DISASTER_RELIEF, SkillEnum.FIRST_AID, SkillEnum.HEALTHCARE, SkillEnum.LOGISTICS));
        relatedSkills.put(SkillEnum.DISASTER_RELIEF, Set.of(SkillEnum.EMERGENCY_RELIEF, SkillEnum.FIRST_AID, SkillEnum.LOGISTICS, SkillEnum.COMMUNITY_OUTREACH));

        // Nhóm Social & Community
        relatedSkills.put(SkillEnum.SOCIAL_WORK, Set.of(SkillEnum.COUNSELING, SkillEnum.COMMUNITY_OUTREACH, SkillEnum.COMMUNICATION));
        relatedSkills.put(SkillEnum.COMMUNITY_OUTREACH, Set.of(SkillEnum.SOCIAL_WORK, SkillEnum.COMMUNICATION, SkillEnum.EVENT_ORGANIZATION, SkillEnum.FUNDRAISING));
        relatedSkills.put(SkillEnum.FUNDRAISING, Set.of(SkillEnum.COMMUNITY_OUTREACH, SkillEnum.EVENT_ORGANIZATION, SkillEnum.COMMUNICATION));

        // Nhóm Organization & Logistics
        relatedSkills.put(SkillEnum.EVENT_ORGANIZATION, Set.of(SkillEnum.LOGISTICS, SkillEnum.COMMUNICATION, SkillEnum.COMMUNITY_OUTREACH, SkillEnum.FUNDRAISING));
        relatedSkills.put(SkillEnum.LOGISTICS, Set.of(SkillEnum.EVENT_ORGANIZATION, SkillEnum.TRANSPORTATION, SkillEnum.EMERGENCY_RELIEF, SkillEnum.DISASTER_RELIEF));
        relatedSkills.put(SkillEnum.TRANSPORTATION, Set.of(SkillEnum.LOGISTICS, SkillEnum.DISASTER_RELIEF, SkillEnum.COMMUNITY_OUTREACH));

        // Nhóm Technical & Support
        relatedSkills.put(SkillEnum.IT_SUPPORT, Set.of(SkillEnum.COMMUNICATION, SkillEnum.TEACHING));

        // Nhóm Domestic & Service
        relatedSkills.put(SkillEnum.COOKING, Set.of(SkillEnum.COMMUNITY_OUTREACH, SkillEnum.EVENT_ORGANIZATION));
        relatedSkills.put(SkillEnum.CLEANING, Set.of(SkillEnum.ENVIRONMENTAL_CLEANUP, SkillEnum.COMMUNITY_OUTREACH));
        relatedSkills.put(SkillEnum.ENVIRONMENTAL_CLEANUP, Set.of(SkillEnum.CLEANING, SkillEnum.COMMUNITY_OUTREACH, SkillEnum.EVENT_ORGANIZATION));

        Set<SkillEnum> matchedRelatedSkills = new HashSet<>();

        // Tìm các skill liên quan
        for (SkillEnum required : requiredSkills) {
            Set<SkillEnum> related = relatedSkills.getOrDefault(required, Collections.emptySet());
            for (SkillEnum volunteerSkill : volunteerSkills) {
                if (related.contains(volunteerSkill)) {
                    matchedRelatedSkills.add(volunteerSkill);
                }
            }
        }

        // Tính điểm: skill liên quan có trọng số thấp hơn skill trùng khớp hoàn toàn
        double relatedScore = matchedRelatedSkills.size(); // 50% giá trị của skill trùng khớp

        return relatedScore;
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

    private String generateMatchReason() {
        List<String> allReasons = List.of(
                "Kỹ năng phù hợp cao",
                "Thời gian rảnh đủ",
                "Khoảng cách gần",
                "Đánh giá tốt",
                "Từng hợp tác trước",
                "Có thái độ tích cực",
                "Ưu tiên khu vực này"
        );

        // Shuffle danh sách để chọn ngẫu nhiên
        List<String> shuffledReasons = new ArrayList<>(allReasons);
        Collections.shuffle(shuffledReasons);

        // Chọn ngẫu nhiên từ 2 đến 3 lý do
        int pickCount = new Random().nextInt(2) + 2; // 2 hoặc 3
        pickCount = Math.min(pickCount, shuffledReasons.size());

        List<String> selected = shuffledReasons.subList(0, pickCount);

        return String.join(", ", selected);
    }



}
