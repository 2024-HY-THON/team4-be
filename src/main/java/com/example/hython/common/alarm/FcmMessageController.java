package com.example.hython.common.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmMessageController {

    private final FcmMessageService fcmMessageService;


    @PostMapping("/api/v1/fcm/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody FcmMessageRequestDto requestDto) {
        String response = fcmMessageService.sendMessage(requestDto);
        return ResponseEntity.ok(response);
    }
}