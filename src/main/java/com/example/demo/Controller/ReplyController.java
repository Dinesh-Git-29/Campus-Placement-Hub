package com.example.demo.Controller;

import com.example.demo.Model.Comment;
import com.example.demo.Model.Reply;
import com.example.demo.Repository.CommentRepository;
import com.example.demo.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyRepository replyRepo;

    @Autowired
    private CommentRepository commentRepo;

    @PostMapping
    public ResponseEntity<?> addReply(@RequestBody Reply replyRequest) {
        try {
            Optional<Comment> commentOptional = commentRepo.findById(replyRequest.getComment().getId());

            if (!commentOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found.");
            }

            Reply reply = new Reply();
            reply.setUsername(replyRequest.getUsername());
            reply.setReplyText(replyRequest.getReplyText());
            reply.setComment(commentOptional.get());

            return ResponseEntity.ok(replyRepo.save(reply));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving reply.");
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List<Reply>> getReplies(@PathVariable Long commentId) {
        List<Reply> replies = replyRepo.findByCommentId(commentId);
        return ResponseEntity.ok(replies);
    }
}
