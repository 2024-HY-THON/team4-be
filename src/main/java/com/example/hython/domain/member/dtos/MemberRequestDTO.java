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
}
