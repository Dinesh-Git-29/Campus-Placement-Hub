package com.example.demo.Controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    private Map<String, String> otpStorage = new HashMap<>();

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String emailOrPhone, @RequestParam String password ,HttpSession session) {
        session.setAttribute("email",emailOrPhone);
        return authService.loginUser(emailOrPhone, password);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Received Request: " + request+email);
        if (userRepository.findByEmail(email)==null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Email not registered"));
        }

        String otp = generateOTP();
        otpStorage.put(email, otp);
        sendEmail(email, "Your OTP Code", "Your OTP is: " + otp);

        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (!otpStorage.containsKey(email) || !otpStorage.get(email).equals(otp)) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid OTP"));
        }

        updatePassword(email, newPassword);
        otpStorage.remove(email);

        return ResponseEntity.ok(Map.of("success", true, "message", "Password reset successful"));
    }

    private String generateOTP() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private boolean isUserRegistered(String email) {
        // Replace with actual database check
        return email.equals("student@example.com");
    }

    private void updatePassword(String email, String newPassword) {
        // Update user password in the database
        System.out.println("Password updated for: " + email);
    }
}
