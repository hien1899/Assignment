package com.example.severdemo.controller;

import com.example.severdemo.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final UserServiceImpl userServiceImpl;
    @GetMapping("/a")
    public String test(){
        userServiceImpl.testService();
        return null;
    }
}
