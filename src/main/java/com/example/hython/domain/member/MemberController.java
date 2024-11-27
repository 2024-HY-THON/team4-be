package com.example.hython.domain.member;


import com.example.hython.common.response.BaseResponse;
import com.example.hython.domain.member.dtos.MemberRequestDTO;
import com.example.hython.domain.member.dtos.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sum/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @PostMapping("/signup")
    public BaseResponse<MemberResponseDTO.MemberSignUpResponseDTO> signup (@RequestBody MemberRequestDTO.MemberSignUpRequestDTO requestDto) {
        return new BaseResponse<>(memberService.signup(requestDto));
    }
}
