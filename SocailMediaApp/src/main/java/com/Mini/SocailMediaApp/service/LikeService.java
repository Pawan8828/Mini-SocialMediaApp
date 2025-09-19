package com.Mini.SocailMediaApp.service;

import com.Mini.SocailMediaApp.model.Like;
import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // Like a post
    public Like likePost(User user, Post post) {
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            throw new RuntimeException("Already liked");
        }
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        return likeRepository.save(like);
    }

    // Unlike a post
    public void unlikePost(User user, Post post) {
        Optional<Like> like = likeRepository.findByUserAndPost(user, post);
        like.ifPresent(likeRepository::delete);
    }

    // Get likes for a post
    public List<Like> getLikesByPost(Post post) {
        return likeRepository.findByPost(post);
    }
}
