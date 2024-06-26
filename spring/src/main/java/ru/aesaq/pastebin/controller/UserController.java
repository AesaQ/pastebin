package ru.aesaq.pastebin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aesaq.pastebin.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
    final HttpSession session;

    public UserController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/info")
    public String getInfo() {
        User user = (User) session.getAttribute("user");
        return user.getUsername();
    }

}
