package com.Mini.SocailMediaApp.service;

import com.Mini.SocailMediaApp.model.Comment;
import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public void deleteComment(Long id, String email) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Optional: only allow comment owner to delete
        if (!comment.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not allowed to delete this comment!");
        }

        commentRepository.delete(comment);
    }
}

