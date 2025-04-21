package com.example.demo.Controller;

import com.example.demo.Model.Comment;
import com.example.demo.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseEntity<?> addComment(@RequestParam String username,
                                        @RequestParam String commentText,
                                        @RequestParam(required = false) MultipartFile file) {
        try {
            Comment comment = new Comment();
            comment.setUsername(username);
            comment.setCommentText(commentText);

            // ✅ Ensure Upload Directory Exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // ✅ Validate and Save File
            if (file != null && !file.isEmpty()) {
                if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds the limit of 5MB");
                }

                String filePath = UPLOAD_DIR + file.getOriginalFilename();
                Path path = Paths.get(filePath);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                comment.setFileUrl(file.getOriginalFilename());
            }

            Comment savedComment = commentRepo.save(comment);
            return ResponseEntity.ok(savedComment);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving comment or file.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        List<Comment> comments = commentRepo.findAll();
        return ResponseEntity.ok(comments);
    }
}
