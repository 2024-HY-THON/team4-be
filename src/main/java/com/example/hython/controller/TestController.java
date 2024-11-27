package com.example.hython.controller;


import com.example.hython.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@CrossOrigin
@Tag(name = "Test Controller", description = "Test Controller")
@RestController
public class TestController {

    @GetMapping("/health")
    public BaseResponse<String> healthCheck() {
        return new BaseResponse("I'm alive");
    }
}
