package com.Mini.SocailMediaApp.repository;

import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
