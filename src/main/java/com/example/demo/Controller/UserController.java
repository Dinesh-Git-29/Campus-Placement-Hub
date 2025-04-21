package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam String email, @RequestParam String otp) {
        return userService.verifyOTP(email, otp);
    }

    @GetMapping("/resend-otp")
    public String resendOTP(@RequestParam String email) {
        return userService.resendOTP(email);
    }
}
