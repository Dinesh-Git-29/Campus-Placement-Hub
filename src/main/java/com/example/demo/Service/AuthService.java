package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Map<String, Object> loginUser(String emailOrPhone, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(emailOrPhone);

        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByPhone(emailOrPhone);
            if (optionalUser.isEmpty()) {
                return Map.of("status", "error", "message", "User not found!");
            }
        }

        User user = optionalUser.get();
        if (!user.isVerified()) {
            return Map.of("status", "error", "message", "Email not verified!");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Map.of("status", "error", "message", "Incorrect password!");
        }

        // Success response with user details
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());

        return response;
    }
}