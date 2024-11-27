package com.example.hython.common.alarm;

import com.example.hython.domain.member.MemberService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmMessageService {

    private final MemberService memberService;

    public String sendMessage(FcmMessageRequestDto requestDto) {
        // 사용자의 Firebase 토큰 값을 조회
        String userFirebaseToken = memberService.findFirebaseTokenByMemberId(requestDto.getMemberId());

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