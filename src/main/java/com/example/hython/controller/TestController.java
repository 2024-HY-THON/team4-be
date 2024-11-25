package com.example.hython.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class TestController {

    @RequestMapping("/health")
    public String healthCheck() {
        return "Hello, World!";
    }
}
