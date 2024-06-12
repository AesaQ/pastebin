package ru.aesaq.pastebin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.aesaq.pastebin.entity.User;
import ru.aesaq.pastebin.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {
        return authenticationService.login(user, session);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        return authenticationService.logout(session);
    }
}
