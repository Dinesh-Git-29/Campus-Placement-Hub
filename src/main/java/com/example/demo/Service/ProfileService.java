package com.example.demo.Service;

import com.example.demo.Model.Profile;
import com.example.demo.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public String saveProfile(Profile profile) {
        profileRepository.save(profile);
        return "Profile updated successfully!";
    }

    public Optional<Profile> getProfile(String email) {
        return profileRepository.findById(email);
    }

    public List<Profile> getAllStudents(){
        return profileRepository.findAll();
    }

    public List<Profile> getEligibleStudents(double minTenth, double minTwelfth, double minCGPA, int maxArrears, String arrearHistory) {
        return profileRepository.findEligibleProfile(minTenth,minTwelfth,minCGPA,maxArrears,arrearHistory);
    }
}
