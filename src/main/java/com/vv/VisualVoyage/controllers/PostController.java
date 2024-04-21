package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.PostSaveDto;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private PostService postService;
    private AuthenticationService authenticationService;

    @Autowired
    public PostController(PostService postService, AuthenticationService authenticationService) {
        this.postService = postService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/posts/")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostSaveDto postSaveDto, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        PostResponse post = postService.createNewPost(postSaveDto, requestUser.getId());
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    @GetMapping("/posts/all")
    public ResponseEntity<List<PostResponse>> findAllPosts(){
        List<PostResponse> posts = postService.findAllPosts();

        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostResponse>> findPostsByUserId(@PathVariable long userId){
        List<PostResponse> posts = postService.findPostsByUserId(userId);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable long postId){
        PostResponse post = postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<PostResponse> savedPost(@PathVariable long postId, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return ResponseEntity.ok(postService.savedPost(postId, requestUser.getId()));
    }
    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<PostResponse> likePost(@PathVariable long postId, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return ResponseEntity.ok(postService.likePost(postId, requestUser.getId()));
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, requestUser.getId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
