package com.example.demo.Service;

import com.example.demo.Model.Resource;
import com.example.demo.Repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    private static final String UPLOAD_DIR = "D:/uploads";

    public void saveResource(String category, String title, MultipartFile file) throws IOException {
        Resource resource = new Resource();
        resource.setCategory(category);
        resource.setTitle(title);
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create directory if it doesn't exist
        }

        String fileName = file.getOriginalFilename();
        File filePath = new File("D:/uploads/"+fileName);

        // Save the file to the directory
        file.transferTo(filePath);

        resource.setFilePath(UPLOAD_DIR + "/" + fileName);
        resourceRepository.save(resource);
    }

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    public Resource getResourceById(Long id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        return resource.orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    public List<Resource> getResourcesByCategory(String category) {
        return resourceRepository.findByCategory(category);
    }
}
