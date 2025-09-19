package com.Mini.SocailMediaApp.controller;

import com.Mini.SocailMediaApp.model.Follow;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.service.FollowService;
import com.Mini.SocailMediaApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows") // plural and consistent
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    public FollowController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    // Follow a user (logged-in user follows someone else)
    @PostMapping("/{followingId}")
    public ResponseEntity<Follow> followUser(@PathVariable Long followingId, Authentication auth) {
        String email = auth.getName();                      // comes from JWT
        User follower = userService.findByEmail(email);     // logged-in user
        User following = userService.getUserById(followingId);
        return ResponseEntity.ok(followService.followUser(follower, following));
    }

    // Unfollow a user
    @DeleteMapping("/{followingId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followingId, Authentication auth) {
        String email = auth.getName();
        User follower = userService.findByEmail(email);
        User following = userService.getUserById(followingId);
        followService.unfollowUser(follower, following);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    // Get followers of a user
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(followService.getFollowers(user));
    }

    // Get list of people a user is following
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<Follow>> getFollowing(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(followService.getFollowing(user));
    }
}