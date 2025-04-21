package com.example.demo.Controller;

import com.example.demo.Model.Resource;
import com.example.demo.Service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResource(@RequestParam String category, @RequestParam String title,
                                                 @RequestParam MultipartFile file) {
        try {
            resourceService.saveResource(category, title, file);
            return ResponseEntity.ok("Resource uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving resource!");
        }
    }

    @GetMapping("/list")
    public List<Resource> listResources() {
        return resourceService.getAllResources();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.ok("Resource deleted successfully!");
    }

    @GetMapping("/lists")
    public List<Resource> listResources(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return resourceService.getResourcesByCategory(category);
        }
        return resourceService.getAllResources();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<UrlResource> downloadFile(@PathVariable Long id) {
        try {
            // Get resource from database
            Resource resource = resourceService.getResourceById(id);

            // Resolve the file path
            Path filePath = Paths.get(resource.getFilePath());
            UrlResource fileResource = new UrlResource(filePath.toUri());

            if (!fileResource.exists() || !fileResource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileResource.getFilename())
                    .body(fileResource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
