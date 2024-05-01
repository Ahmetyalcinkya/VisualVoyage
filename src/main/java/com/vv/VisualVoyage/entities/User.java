package com.vv.VisualVoyage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "First name is mandatory!")
    @Size(min = 2, max = 20, message = "First name length must be between 2 and 20 characters!")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "First name is mandatory!")
    @Size(min = 2, max = 20, message = "First name length must be between 2 and 20 characters!")
    @NotNull
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Please enter a valid email!")
    @NotNull
    private String email;

    @Column(name = "password")
    @Size(min = 6, message = "Password length must be at least 6 characters!")
    @NotNull
    private String password;

    @Column(name = "gender")
    @NotBlank(message = "Gender is mandatory!")
    @NotNull
    private String gender;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "cover_picture")
    private String coverPicture;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "biography")
    private String biography;

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
