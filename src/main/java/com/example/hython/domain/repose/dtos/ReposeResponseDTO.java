package com.example.hython.domain.repose.dtos;

import jakarta.persistence.criteria.CriteriaBuilder.In;
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

}
