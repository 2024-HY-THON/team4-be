package com.example.hython.domain.repose.dtos;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReposeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReposeDTO {
        private Long reposeId;
        private String todo;
        private LocalTime startTime;
        private Integer remainingMinutes;
        private Integer remainingSeconds;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReposesDTO{
        private List<ReposeDTO> reposeDTOs;
        private Integer reposeCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReposeDetailDTO {
        private Long reposeId;
        private String todayDefinition;
        private String todayEmotion;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReposeCalendarDetailDTO {
        private Long reposeId;
        private Integer reposeTotalMinutes;
        private String todayEmotion;
        private LocalDate date;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReposeCalendarDTO {
        private Integer year;
        private Integer month;
        private List<ReposeCalendarDetailDTO> reposeCalendarDetailDTOs;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReposeContentDTO {
        private Long reposeId;
        private boolean isDone;
        private Integer reposeTotalMinutes;
        private String todayEmotion;
        private String todayDefinition;

        private Integer recipeSatisfaction;
        private String recipeDefinition;

        private LocalDate date;
    }

}
