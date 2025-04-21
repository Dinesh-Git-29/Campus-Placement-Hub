package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = true)
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Register user and send OTP
    public String registerUser(User user) {
        if (userRepository.existsById(user.getEmail())) {
            return "Email already registered!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.generateOTP(); // Generate OTP and expiry
        userRepository.save(user);

        // Send OTP via email
        emailService.sendOTPEmail(user.getEmail(), user.getOtp());

        return "Registration successful! Check your email for OTP (valid for 5 minutes).";
    }

    // Verify OTP
    public String verifyOTP(String email, String otp) {
        Optional<User> optionalUser = userRepository.findById(email);
        if (optionalUser.isEmpty()) {
            return "User not found!";
        }

        User user = optionalUser.get();

        // Check if OTP is expired
        if (LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            return "OTP expired! Request a new OTP.";
        }

        // Check if OTP matches
        if (!user.getOtp().equals(otp)) {
            return "Invalid OTP!";
        }

        // Verify user
        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);

        return "Email verified successfully!";
    }

    // Resend OTP
    public String resendOTP(String email) {
        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isEmpty()) {
            return "Email not found!";
        }

        User user = optionalUser.get();

        if (user.isVerified()) {
            return "Your email is already verified!";
        }

        // Generate new OTP
        user.generateOTP();
        userRepository.save(user);

        // Send OTP via email
        emailService.sendOTPEmail(user.getEmail(), user.getOtp());

        return "New OTP sent! Check your inbox (valid for 5 minutes).";
    }
}
