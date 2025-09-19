package com.Mini.SocailMediaApp.repository;

import com.Mini.SocailMediaApp.model.Like;
import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByPost(Post post);
}
