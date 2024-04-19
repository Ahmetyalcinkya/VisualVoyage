package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.PostSaveDto;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.entities.Post;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.repositories.PostRepository;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!
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
                .build();
    }

    @Override
    public String deletePost(long postId, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post with the given id is not exist!")); //TODO VisualVoyageException will be added!
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!
        if(Objects.equals(post.getUser().getId(), user.getId())){
            postRepository.delete(post);
            return "Post successfully deleted!";
        }
        throw new RuntimeException("You can't delete someone else's post!");
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
                .build()).collect(Collectors.toList());
    }

    @Override
    public PostResponse findPostById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post with the given id is not exist!")); //TODO VisualVoyageException will be added!
        return PostResponse.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .image(post.getImage())
                .video(post.getVideo())
                .createdAt(post.getCreatedAt())
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
                .build()).collect(Collectors.toList());
    }

    @Override
    public PostResponse savedPost(long postId, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post with the given id is not exist!")); //TODO VisualVoyageException will be added!
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!

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
                .build();
    }

    @Override
    public PostResponse likePost(long postId, long userId) { //TODO THIS METHOD SHOULD BE CHECKED!
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post with the given id is not exist!")); //TODO VisualVoyageException will be added!
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!

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
                .build();
    }
}
