package com.example.hython.domain.member.dtos;

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
    }

    @Getter
    @RequiredArgsConstructor
    public static class RecipeRequestDTO {
        private String recipe;
    }
}
