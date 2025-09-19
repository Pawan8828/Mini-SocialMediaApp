package com.Mini.SocailMediaApp.controller;

import com.Mini.SocailMediaApp.model.Comment;
import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.service.CommentService;
import com.Mini.SocailMediaApp.service.PostService;
import com.Mini.SocailMediaApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    // Add comment to a post (with logged-in user)
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long postId, @RequestBody Comment comment, Authentication authentication) {
        // 1. Get logged-in user email from JWT
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        // 2. Get post
        Post post = postService.getPostById(postId);

        // 3. Attach
        comment.setPost(post);
        comment.setUser(user);

        // 4. Save
        return ResponseEntity.ok(commentService.addComment(comment));
    }

    // Get comments for a post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(commentService.getCommentsByPost(post));
    }

    // Delete comment (only by owner — optional check)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        commentService.deleteComment(id, email); // update service to verify ownership
        return ResponseEntity.ok("Comment deleted successfully");
    }
}