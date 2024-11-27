package com.example.hython.domain.member;


import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import com.example.hython.domain.member.dtos.MemberRequestDTO;
import com.example.hython.domain.member.dtos.MemberResponseDTO;
import com.example.hython.domain.member.utils.JWTUtils;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JWTUtils jwtUtils;

    @Transactional
    public MemberResponseDTO.MemberSignUpResponseDTO signup(MemberRequestDTO.MemberSignUpRequestDTO requestDto) {

        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .build();

        memberRepository.save(member);

        return MemberResponseDTO.MemberSignUpResponseDTO.builder()
                .email(member.getEmail())
                .accessToken(jwtUtils.generateToken(member.getId()))
                .build();
    }
}
