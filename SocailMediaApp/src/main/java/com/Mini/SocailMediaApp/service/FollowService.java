package com.Mini.SocailMediaApp.service;

import com.Mini.SocailMediaApp.model.Follow;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    // Follow a user
    public Follow followUser(User follower, User following) {
        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (existingFollow.isPresent()) {
            throw new RuntimeException("Already following");
        }
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        return followRepository.save(follow);
    }

    // Unfollow a user
    public void unfollowUser(User follower, User following) {
        Optional<Follow> follow = followRepository.findByFollowerAndFollowing(follower, following);
        follow.ifPresent(followRepository::delete);
    }

    // Get all followers of a user
    public List<Follow> getFollowers(User user) {
        return followRepository.findByFollowing(user);
    }

    // Get all following users
    public List<Follow> getFollowing(User user) {
        return followRepository.findByFollower(user);
    }
}
