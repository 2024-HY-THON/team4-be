package com.example.hython.domain.member;


import com.example.hython.common.exception.BaseException;
import com.example.hython.common.response.BaseResponseStatus;
import com.example.hython.domain.member.dtos.MemberRequestDTO;
import com.example.hython.domain.member.dtos.MemberResponseDTO;
import com.example.hython.domain.member.utils.JWTUtils;
import java.util.List;
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
                .profileImageUrl("defalut")
                .phoneNumber(requestDto.getPhoneNumber())
                .name(requestDto.getName())
                .build();

        memberRepository.save(member);

        return MemberResponseDTO.MemberSignUpResponseDTO.builder()
                .email(member.getEmail())
                .accessToken(jwtUtils.generateToken(member.getId()))
                .build();
    }

    @Transactional
    public MemberResponseDTO.MemberSignUpResponseDTO signin(MemberRequestDTO.MemberSignInRequestDTO requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER));

        if (!member.getPassword().equals(requestDto.getPassword())) {
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD);
        }

        return MemberResponseDTO.MemberSignUpResponseDTO.builder()
                .email(member.getEmail())
                .accessToken(jwtUtils.generateToken(member.getId()))
                .build();
    }

    @Transactional
    public MemberResponseDTO.MemberSignUpResponseDTO updateInfo(MemberRequestDTO.MemberUpdateRequestDTO requestDto) {
        String token = jwtUtils.getToken();
        Long memberId = jwtUtils.getMemberIdByToken(token);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER));

        member.updateInfo(requestDto.getPassword(), requestDto.getName(), requestDto.getPhoneNumber());

        return MemberResponseDTO.MemberSignUpResponseDTO.builder()
                .email(member.getEmail())
                .accessToken(jwtUtils.generateToken(member.getId()))
                .build();
    }

    @Transactional
    public MemberResponseDTO.ProfileResponseDTO getMyProfile() {
        String token = jwtUtils.getToken();
        Long memberId = jwtUtils.getMemberIdByToken(token);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_MEMBER));

        return MemberResponseDTO.ProfileResponseDTO.builder()
                .name(member.getName())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

}
