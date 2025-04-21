package com.example.demo.Service;

import com.example.demo.Model.Experience;
import com.example.demo.Repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    private static final String UPLOAD_DIR = "D:/uploads/"; // Store uploads in project root

    public void saveExperience(String company, String role, String rounds, MultipartFile file) throws IOException {
        Experience experience = new Experience();
        experience.setCompany(company);
        experience.setRole(role);
        experience.setRounds(rounds);

        // ✅ Create directory if it does not exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Creates the directory
        }
        else{
            System.out.println("true");
        }

        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File filePath = new File(uploadDir, fileName);
            file.transferTo(filePath); // Save the file
            experience.setFilePath(UPLOAD_DIR + File.separator + fileName);
        }

        experienceRepository.save(experience);
    }


    public List<Experience> findByCompanyAndRole(String company, String role) {
        return experienceRepository.findByCompanyAndRole(company, role);
    }
}
