package com.vv.VisualVoyage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", schema = "social")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    private Set<Long> followers = new HashSet<>();

    private Set<Long> followings = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_liked", schema = "social",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likedPosts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_saved", schema = "social",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> savedPost = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reel> reels;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_comment_liked", schema = "social",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<Comment> likedComments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "user_chat", schema = "social",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Message> messages = new ArrayList<>();
}
