package com.techie.springconnect.service;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.techie.springconnect.dto.PostRequest;
import com.techie.springconnect.dto.PostResponse;
import com.techie.springconnect.exceptions.SpringConnectException;
import com.techie.springconnect.model.Post;
import com.techie.springconnect.model.Subpost;
import com.techie.springconnect.model.Vote;
import com.techie.springconnect.model.VoteType;
import com.techie.springconnect.repository.CommentRepository;
import com.techie.springconnect.repository.PostRepository;
import com.techie.springconnect.repository.SubPostRepository;
import com.techie.springconnect.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.techie.springconnect.model.VoteType.DOWNVOTE;
import static com.techie.springconnect.model.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubPostRepository subPostRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;

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

    public List<PostResponse> getAllPostResponses() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    private PostResponse mapToPostResponse(Post post) {
        return new PostResponse(
                post.getPostId(),                      // Mapping postId to id
                post.getPostName(),                       // Assuming postName corresponds to title
                post.getUrl(),                         // Mapping url directly
                post.getDescription(),                 // Mapping description directly
                post.getUser().getUsername(),          // Mapping username from User entity
                post.getSubpost().getName(),         // Mapping subreddit name
                post.getVoteCount(),                   // Mapping voteCount directly
                commentCount(post),                    // Mapping commentCount using custom method
                getDuration(post),                     // Mapping duration using custom method
                isPostUpVoted(post),                   // Mapping upVote using custom method
                isPostDownVoted(post)                  // Mapping downVote using custom method
        );
    }

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
