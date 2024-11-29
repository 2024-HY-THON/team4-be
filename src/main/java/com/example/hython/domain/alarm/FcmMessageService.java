package com.example.hython.domain.alarm;

import com.example.hython.domain.member.MemberService;
import com.example.hython.domain.member.utils.JWTUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmMessageService {

    private final MemberService memberService;
    private final JWTUtils jwtUtils;

    public String sendMessage(FcmMessageRequestDTO requestDto) {

        Long memberIdByToken = jwtUtils.getMemberIdByToken(jwtUtils.getToken());
        // 사용자의 Firebase 토큰 값을 조회
        String userFirebaseToken = memberService.findFirebaseTokenByMemberId(memberIdByToken);

        // 메시지 구성
        Message message = Message.builder()
                .putData("title", requestDto.getTitle())
                .putData("content", requestDto.getBody())
                .setToken(userFirebaseToken) // 조회한 토큰 값을 사용
                .build();
        try {
            // 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            return "Message sent successfully: " + response;
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Failed to send message";
        }
    }
}