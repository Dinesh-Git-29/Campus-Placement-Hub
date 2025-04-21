package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String email; // Primary Key

    private String name;
    private String phone;
    private String password;
    private String role = "Student"; // Default role is Student
    private boolean isVerified = false; // Check if email is verified

    private String otp; // Store OTP
    private LocalDateTime otpExpiry; // Expiry time for OTP

    public void generateOTP() {
        this.otp = String.format("%06d", new Random().nextInt(1000000)); // 6-digit OTP
        this.otpExpiry = LocalDateTime.now().plusMinutes(5); // OTP expires in 5 minutes
    }
}
