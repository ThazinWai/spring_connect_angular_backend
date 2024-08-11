package com.techie.springconnect.service;

import com.techie.springconnect.dto.PostRequest;
import com.techie.springconnect.exceptions.SpringConnectException;
import com.techie.springconnect.model.Post;
import com.techie.springconnect.model.Subpost;
import com.techie.springconnect.repository.PostRepository;
import com.techie.springconnect.repository.SubPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubPostRepository subPostRepository;
    private final AuthService authService;

    public void save(PostRequest postRequest) {
        Subpost subpost = subPostRepository.findByName(postRequest.getSubpostName())
                .orElseThrow(() -> new SpringConnectException(postRequest.getPostName()));

        Post post = new Post();
        post.setPostId(postRequest.getPostId());
        post.setPostName(postRequest.getPostName());
        post.setUrl(postRequest.getUrl());
        post.setDescription(postRequest.getDescription());
        post.setCreatedDate(Instant.now());
        post.setSubpost(subpost);
        post.setUser(authService.getCurrentUser());

        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
