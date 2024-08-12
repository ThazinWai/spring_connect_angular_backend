package com.techie.springconnect.repository;

import com.techie.springconnect.model.Comment;
import com.techie.springconnect.model.Post;
import com.techie.springconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
