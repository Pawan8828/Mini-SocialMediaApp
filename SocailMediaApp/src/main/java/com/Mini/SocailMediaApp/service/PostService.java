package com.Mini.SocailMediaApp.service;

import com.Mini.SocailMediaApp.model.Post;
import com.Mini.SocailMediaApp.model.User;
import com.Mini.SocailMediaApp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Create new post
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get posts by user
    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUser(user);
    }

    // Get post by ID
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // Delete post
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }

}
