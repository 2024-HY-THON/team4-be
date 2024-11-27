package com.example.hython.domain.member.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MemberRequestDTO {

    @Getter
    @RequiredArgsConstructor
    public static class MemberSignUpRequestDTO {
        private String email;
        private String password;
        private String name;
        private String phoneNumber;
    }

    @Getter
    @RequiredArgsConstructor
    public static class MemberSignInRequestDTO{
        private String email;
        private String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static class MemberUpdateRequestDTO{
        private String password;
        private String name;
        private String phoneNumber;
        private String profileImageUrl;
        private LocalDate birth;
    }

    @Getter
    @RequiredArgsConstructor
    public static class RecipeRequestDTO {
        private Integer minutes;
        private Integer satisfaction;
        private String definition;
        private String recipe;
    }

    @Getter
    @RequiredArgsConstructor
    public static class RecipeDeleteRequestDTO {
        private String recipe;
    }


    @Getter
    @RequiredArgsConstructor
    public static class UpdateFCMTokenRequestDTO{
        private String fcmToken;
    }
}
