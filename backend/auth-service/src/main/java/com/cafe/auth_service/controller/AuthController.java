package com.cafe.auth_service.controller;

import com.cafe.auth_service.dto.LoginRequest;
import com.cafe.auth_service.dto.RegisterRequest;
import com.cafe.auth_service.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String test() {
        return "Auth Service Running";
    }

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginRequest request) {

        String token = userService.login(request);

        return Map.of("token", token);
    }
}