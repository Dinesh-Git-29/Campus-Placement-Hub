package com.example.demo.Repository;

import com.example.demo.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment save(Comment comment);

    List<Comment> findAll();
}