package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.PostSaveDto;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.Post;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.exceptions.VisualVoyageExceptions;
import com.vv.VisualVoyage.repositories.PostRepository;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostManager implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostManager(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostResponse createNewPost(PostSaveDto postSaveDto, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!", HttpStatus.NOT_FOUND));
        Post post = Post.builder()
                .caption(postSaveDto.getCaption())
                .image(postSaveDto.getImage())
                .video(postSaveDto.getVideo())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Post saved = postRepository.save(post);
        return PostResponse.builder()
                .id(saved.getId())
                .caption(saved.getCaption())
                .image(saved.getImage())
                .video(saved.getVideo())
                .createdAt(saved.getCreatedAt())
                .userId(saved.getUser().getId())
                .build();
    }

    @Override
    public String deletePost(long postId, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new VisualVoyageExceptions("Post with the given id is not exist!", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!", HttpStatus.NOT_FOUND));
        if(Objects.equals(post.getUser().getId(), user.getId())){
            postRepository.delete(post);
            return "Post successfully deleted!";
        }
        throw new VisualVoyageExceptions("You can't delete someone else's post!", HttpStatus.FORBIDDEN);
    }

    @Override
    public List<PostResponse> findPostsByUserId(long userId) {
        List<Post> posts = postRepository.findPostsByUserId(userId);

        return posts.stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .build()).collect(Collectors.toList());
    }

    @Override
    public PostResponse findPostById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new VisualVoyageExceptions("Post with the given id is not exist!", HttpStatus.NOT_FOUND));
        return PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .liked(post.getLiked().stream().map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .profilePicture(user.getProfilePicture())
                        .username(user.getUsername())
                        .build()).toList())
                .build();
    }

    @Override
    public List<PostResponse> findAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .liked(post.getLiked().stream().map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .profilePicture(user.getProfilePicture())
                        .username(user.getUsername())
                        .build()).toList())
                .build()).collect(Collectors.toList());
    }

    @Override
    public PostResponse savedPost(long postId, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new VisualVoyageExceptions("Post with the given id is not exist!", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!", HttpStatus.NOT_FOUND));

        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
        }else {
            user.getSavedPost().add(post);
        }
        userRepository.save(user);
        return PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .build();
    }

    @Override
    public PostResponse likePost(long postId, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new VisualVoyageExceptions("Post with the given id is not exist!", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!",HttpStatus.NOT_FOUND));

        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        }
        else {
            post.getLiked().add(user);
        }

        postRepository.save(post);

        return PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .liked(post.getLiked().stream().map(likedUser -> UserResponse.builder()
                        .id(likedUser.getId())
                        .firstName(likedUser.getFirstName())
                        .lastName(likedUser.getLastName())
                        .profilePicture(likedUser.getProfilePicture())
                        .username(likedUser.getUsername())
                        .build()).toList())
                .build();
    }
}
