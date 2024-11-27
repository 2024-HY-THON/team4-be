package com.example.hython.domain.repose.dtos;

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
        private Integer remainingMinutes;
        private Integer remainingSeconds;
    }

}
