package com.example.demo.Controller;

import com.example.demo.Model.Experience;
import com.example.demo.Service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    private static final String UPLOAD_DIR = "D:/uploads/";

    @PostMapping("/add")
    public ResponseEntity<String> addExperience(@RequestParam String company, @RequestParam String role,
                                                @RequestParam String rounds, @RequestParam(required = false) MultipartFile file) {
        try {
            experienceService.saveExperience(company, role, rounds, file);
            return ResponseEntity.ok("Experience added successfully!");
        } catch (IOException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving experience!");
        }
    }

    @GetMapping("/search")
    public List<Experience> searchExperiences(@RequestParam String company, @RequestParam String role) {
        return experienceService.findByCompanyAndRole(company, role);
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}