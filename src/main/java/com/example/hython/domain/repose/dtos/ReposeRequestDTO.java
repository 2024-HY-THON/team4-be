package com.example.hython.domain.repose.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ReposeRequestDTO {

    @Getter
    @RequiredArgsConstructor
    public static class ReposeAddRequestDTO {
        private String todo;
        private Integer minutes;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ReposeStopRequestDTO{
        private Integer remainingSeconds;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ReposeTodayRequestDTO{
        private String todayDefinition;
        private String todayEmotion;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ReposeDetailRequestDTO{
        private LocalDate date;
    }


}
