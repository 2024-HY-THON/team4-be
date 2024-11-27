package com.example.hython.domain.member;


import com.example.hython.common.response.BaseResponse;
import com.example.hython.domain.member.dtos.MemberRequestDTO;
import com.example.hython.domain.member.dtos.MemberResponseDTO;
import com.example.hython.domain.member.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sum/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Tag(name = "회원")
    @Operation(summary = "회원가입 페이지 - 회원가입", description = "회원 가입 API입니다. 이메일, 비밀번호, 이름을 입력받아 회원가입을 진행합니다.")
    @PostMapping("/sign-up")
    public BaseResponse<MemberResponseDTO.MemberSignUpResponseDTO> signup (@RequestBody MemberRequestDTO.MemberSignUpRequestDTO requestDto) {
        return new BaseResponse<>(memberService.signup(requestDto));
    }

    @Tag(name = "회원")
    @Operation(summary = "로그인 페이지 - 로그인", description = "로그인 API입니다. 이메일, 비밀번호를 입력받아 로그인을 진행합니다.")
    @PostMapping("/sign-in")
    public BaseResponse<MemberResponseDTO.MemberSignUpResponseDTO> signin (@RequestBody MemberRequestDTO.MemberSignInRequestDTO requestDto) {
        return new BaseResponse<>(memberService.signin(requestDto));
    }

    @Tag(name = "회원")
    @Operation(summary = "개인 정보 변경 페이지 - 개인 정보 변경", description = "개인 정보 변경 API입니다. 비밀번호, 이름, 전화번호를 입력받아 개인 정보를 변경합니다.")
    @PostMapping("/update-info")
    public BaseResponse<?> updateInfo (@RequestBody MemberRequestDTO.MemberUpdateRequestDTO requestDto) {
        return new BaseResponse<>(memberService.updateInfo(requestDto));
    }

    // 내 프로필 페이지

    @Tag(name = "회원")
    @Operation(summary = "프로필 페이지 - 내 프로필 조회", description = "내 프로필 조회 API입니다. 내 프로필을 조회합니다.")
    @GetMapping("/get-my-profile")
    public BaseResponse<MemberResponseDTO.ProfileResponseDTO> getMyProfile () {
        return new BaseResponse<>(memberService.getMyProfile());
    }





}
