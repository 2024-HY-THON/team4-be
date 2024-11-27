package com.example.hython.domain.alarm;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알람")
@RestController
@RequestMapping("/sum/fcm")
@RequiredArgsConstructor
public class FcmMessageController {

    private final FcmMessageService fcmMessageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody FcmMessageRequestDto requestDto) {
        String response = fcmMessageService.sendMessage(requestDto);
        return ResponseEntity.ok(response);
    }
}