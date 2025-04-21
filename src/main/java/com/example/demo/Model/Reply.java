package com.example.demo.Model;

import com.example.demo.Model.Comment;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}
