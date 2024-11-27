package com.example.hython.domain.alarm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알람")
@CrossOrigin
@RestController
@RequestMapping("/sum/fcm")
@RequiredArgsConstructor
public class FcmMessageController {

    private final FcmMessageService fcmMessageService;

    @Operation(summary = "[구현 중] FCM 알람 - 메시지 전송", description = """
    FCM 메시지를 전송합니다. 추후 구현 예정입니다. 
    """)
    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody FcmMessageRequestDto requestDto) {
        String response = fcmMessageService.sendMessage(requestDto);
        return ResponseEntity.ok(response);
    }
}