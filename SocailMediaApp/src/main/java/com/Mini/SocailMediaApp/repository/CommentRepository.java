package com.Mini.SocailMediaApp.repository;

import com.Mini.SocailMediaApp.model.Comment;
import com.Mini.SocailMediaApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
