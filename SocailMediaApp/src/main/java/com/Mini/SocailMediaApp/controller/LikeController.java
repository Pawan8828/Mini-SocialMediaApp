package com.Mini.SocailMediaApp.controller;

import com.Mini.SocailMediaApp.model.Like;
import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.service.LikeService;
import com.Mini.SocailMediaApp.service.PostService;
import com.Mini.SocailMediaApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    // Like a post
    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Like> likePost(@PathVariable Long userId, @PathVariable Long postId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(likeService.likePost(user, post));
    }

    // Unlike a post
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long userId, @PathVariable Long postId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(postId);
        likeService.unlikePost(user, post);
        return ResponseEntity.ok("Post unliked successfully");
    }

    // Get likes for a post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> getLikesByPost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(likeService.getLikesByPost(post));
    }
}
