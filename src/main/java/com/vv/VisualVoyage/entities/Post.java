package com.vv.VisualVoyage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post", schema = "social")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "caption")
    private String caption;

    @Column(name = "image")
    private String image;

    @Column(name = "video")
    private String video;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany
    private Set<User> liked = new HashSet<>();

    @ManyToOne
    private User user;

}
