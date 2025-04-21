package com.example.demo.Controller;

import com.example.demo.Model.Profile;
import com.example.demo.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/save")
    public String saveProfile(@RequestBody Profile profile) {
        return profileService.saveProfile(profile);
    }

    @GetMapping("/get")
    public Profile getProfile(@RequestParam String email) {
        Optional<Profile> profile = profileService.getProfile(email);
        return profile.orElse(null);
    }
}
