package com.example.hython.domain.alarm;

import com.example.hython.domain.member.MemberService;
import com.example.hython.domain.member.utils.JWTUtils;
import com.example.hython.domain.repose.Repose;
import com.example.hython.domain.repose.ReposeRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmMessageService {

    private final MemberService memberService;
    private final ReposeRepository reposeRepository;
    private final JWTUtils jwtUtils;

    public String sendMessage(FcmMessageRequestDTO requestDto) {

        Long memberIdByToken = jwtUtils.getMemberIdByToken(jwtUtils.getToken());
        // 사용자의 Firebase 토큰 값을 조회
        String userFirebaseToken = memberService.findFirebaseTokenByMemberId(memberIdByToken);

        // 메시지 구성
        Message message = Message.builder()
                .putData("title", requestDto.getTitle())
                .putData("content", requestDto.getBody())
                .putData("restId", requestDto.getRestId())
                .putData("text", requestDto.getText())
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
    @Transactional
    @Scheduled(fixedRate = 20000)// 매 분마다 실행
    public void sendReposeAlarm() {
        // 현재 시간
        LocalTime now = LocalTime.now();
        log.info(now.toString());
        // 현재 시간에서 5분 뒤
        LocalTime fiveMinutesBefore = now.plusMinutes(15);

        // 5분 전에 시작하는 휴식 조회
        List<Repose> reposeList = reposeRepository.findByStartTimeBetween(now, fiveMinutesBefore);

        log.info("reposeList is " + reposeList.toString());
        for (Repose repose : reposeList) {
            if (repose.getIsAlarm()) {
                continue;
            }
            // 사용자의 Firebase 토큰 값을 조회
            String userFirebaseToken = memberService.findFirebaseTokenByMemberId(repose.getMember().getId());

            repose.setIsAlarm(true);
            // 메시지 구성
            Message message = Message.builder()
                    .putData("title", "일상 속 짧은 해방의 순간, 숨")
                    .putData("content", repose.getMinutes() + "분 동안 " + repose.getRecipe().getRecipe() + "(으)로 휴식을 갖는건 어떠신가요?")
                    .putData("restId", repose.getId().toString())
                    .putData("text", repose.getRecipe().getRecipe())
                    .setToken(userFirebaseToken) // 조회한 토큰 값을 사용
                    .build();

            log.info("message is "  + message.toString());

            try {
                // 메시지 전송
                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println("Message sent successfully: " + response); // 성공 로그 출력
            } catch (FirebaseMessagingException e) {
                // 예외 처리
                e.printStackTrace();
                System.out.println("Failed to send message");
            }
        }
    }
}