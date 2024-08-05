package com.techie.springconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subpost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    //@JoinColumn(name="postId",referencedColumnName = "postId")
    private List<Post>posts;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
