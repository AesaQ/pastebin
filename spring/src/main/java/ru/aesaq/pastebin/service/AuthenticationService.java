package ru.aesaq.pastebin.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import ru.aesaq.pastebin.entity.User;
import ru.aesaq.pastebin.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String username, String password) {
        if (username == null || username.equals("")) {
            return "Username is null";
        }
        if (userRepository.findByUsername(username) != null) {
            return "Username already exists";
        }
        userRepository.save(new User(username, password));
        return "User registered successfully";
    }

    public String login(User user, HttpSession session) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return "Invalid username";
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return "Invalid password";
        }
        session.setAttribute("user", existingUser);
        return "Login successful";
    }

    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out successfully";
    }

}
