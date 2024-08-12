package com.techie.springconnect.repository;

import com.techie.springconnect.model.Post;
import com.techie.springconnect.model.User;
import com.techie.springconnect.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
