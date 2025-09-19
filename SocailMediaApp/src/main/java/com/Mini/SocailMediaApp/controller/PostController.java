package com.Mini.SocailMediaApp.controller;

import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.service.PostService;
import com.Mini.SocailMediaApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        // The principal must be your User object returned by UserDetailsService
        return (User) authentication.getPrincipal();
    }

    // Create post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email);
        post.setUser(user);
        return ResponseEntity.ok(postService.createPost(post));
    }

    // Get all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // Get posts by logged-in user
    @GetMapping("/me")
    public ResponseEntity<List<Post>> getMyPosts() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(postService.getPostsByUser(currentUser));
    }

    // Get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Delete post (only owner can delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        postService.deletePost(id, currentUser); // Pass user to check ownership
        return ResponseEntity.ok("Post deleted successfully");
    }
}
