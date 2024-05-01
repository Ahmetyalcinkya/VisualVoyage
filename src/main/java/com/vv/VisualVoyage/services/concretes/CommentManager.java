package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.CommentSaveDto;
import com.vv.VisualVoyage.dtos.responses.CommentResponse;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.Comment;
import com.vv.VisualVoyage.entities.Post;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.exceptions.VisualVoyageExceptions;
import com.vv.VisualVoyage.repositories.CommentRepository;
import com.vv.VisualVoyage.repositories.PostRepository;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentManager implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentManager(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentResponse createComment(CommentSaveDto commentSaveDto, long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new VisualVoyageExceptions("Post with the given id is not exist!", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!", HttpStatus.NOT_FOUND));
        Comment comment = Comment.builder()
                .content(commentSaveDto.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .liked(new ArrayList<>())
                .build();
        commentRepository.save(comment);
        post.getComments().add(comment);
        postRepository.save(post);

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .user(UserResponse.builder()
                        .id(comment.getUser().getId())
                        .firstName(comment.getUser().getFirstName())
                        .lastName(comment.getUser().getLastName())
                        .profilePicture(user.getProfilePicture())
                        .username(user.getUsername())
                        .build())
                .post(PostResponse.builder()
                        .id(comment.getPost().getId())
                        .caption(comment.getPost().getCaption())
                        .image(comment.getPost().getImage())
                        .video(comment.getPost().getVideo())
                        .createdAt(comment.getPost().getCreatedAt())
                        .userId(comment.getPost().getUser().getId())
                        .build())
                .liked(comment.getLiked().stream().map(likedUser -> UserResponse.builder()
                        .id(likedUser.getId())
                        .firstName(likedUser.getFirstName())
                        .lastName(likedUser.getLastName())
                        .profilePicture(likedUser.getProfilePicture())
                        .username(likedUser.getUsername())
                        .build()).toList())
                .build();
    }

    @Override
    public CommentResponse likeComment(long commentId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!", HttpStatus.NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new VisualVoyageExceptions("The comment with the given id is not exist!", HttpStatus.NOT_FOUND));

        if(!comment.getLiked().contains(user)){
            comment.getLiked().add(user);
        }else {
            comment.getLiked().remove(user);
        }
        commentRepository.save(comment);

        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .user(UserResponse.builder()
                        .id(comment.getUser().getId())
                        .firstName(comment.getUser().getFirstName())
                        .lastName(comment.getUser().getLastName())
                        .profilePicture(user.getProfilePicture())
                        .username(user.getUsername())
                        .build())
                .post(PostResponse.builder()
                        .id(comment.getPost().getId())
                        .caption(comment.getPost().getCaption())
                        .image(comment.getPost().getImage())
                        .video(comment.getPost().getVideo())
                        .createdAt(comment.getPost().getCreatedAt())
                        .userId(comment.getPost().getUser().getId())
                        .build())
                .liked(comment.getLiked().stream().map(likedUser -> UserResponse.builder()
                        .id(likedUser.getId())
                        .firstName(likedUser.getFirstName())
                        .lastName(likedUser.getLastName())
                        .profilePicture(likedUser.getProfilePicture())
                        .username(likedUser.getUsername())
                        .build()).toList())
                .build();
    }

    @Override
    public CommentResponse findCommentById(long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new VisualVoyageExceptions("The comment with the given id is not exist!", HttpStatus.NOT_FOUND));
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .user(UserResponse.builder()
                        .id(comment.getUser().getId())
                        .firstName(comment.getUser().getFirstName())
                        .lastName(comment.getUser().getLastName())
                        .profilePicture(comment.getUser().getProfilePicture())
                        .username(comment.getUser().getUsername())
                        .build())
                .post(PostResponse.builder()
                        .id(comment.getPost().getId())
                        .caption(comment.getPost().getCaption())
                        .image(comment.getPost().getImage())
                        .video(comment.getPost().getVideo())
                        .createdAt(comment.getPost().getCreatedAt())
                        .userId(comment.getPost().getUser().getId())
                        .build())
                .liked(comment.getLiked().stream().map(likedUser -> UserResponse.builder()
                        .id(likedUser.getId())
                        .firstName(likedUser.getFirstName())
                        .lastName(likedUser.getLastName())
                        .profilePicture(likedUser.getProfilePicture())
                        .username(likedUser.getUsername())
                        .build()).toList())
                .build();
    }

    @Override
    public List<CommentResponse> findPostsComments(long postId, int offset) {
        List<Comment> comments = commentRepository.findPostsComments(postId, offset);
        return comments.stream().map(comment -> CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .user(UserResponse.builder()
                        .id(comment.getUser().getId())
                        .firstName(comment.getUser().getFirstName())
                        .lastName(comment.getUser().getLastName())
                        .profilePicture(comment.getUser().getProfilePicture())
                        .username(comment.getUser().getUsername())
                        .build())
                .liked(comment.getLiked().stream().map(likedUser -> UserResponse.builder()
                        .id(likedUser.getId())
                        .firstName(likedUser.getFirstName())
                        .lastName(likedUser.getLastName())
                        .profilePicture(likedUser.getProfilePicture())
                        .username(likedUser.getUsername())
                        .build()).toList())
                .build()).toList();
    }
}
