package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.PostSaveDto;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.services.abstracts.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostSaveDto postSaveDto,@PathVariable long userId){ //TODO THIS METHOD WILL BE UPDATED GETAUTHENTICATEDUSER
        PostResponse post = postService.createNewPost(postSaveDto, userId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> findAllPosts(){
        List<PostResponse> posts = postService.findAllPosts();

        return ResponseEntity.ok(posts);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> findPostsByUserId(@PathVariable long userId){
        List<PostResponse> posts = postService.findPostsByUserId(userId);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable long postId){
        PostResponse post = postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/save/{postId}/user/{userId}")
    public ResponseEntity<PostResponse> savedPost(@PathVariable long postId, @PathVariable long userId){
        return ResponseEntity.ok(postService.savedPost(postId, userId));
    }
    @PutMapping("/like/{postId}/user/{userId}")
    public ResponseEntity<PostResponse> likePost(@PathVariable long postId, @PathVariable long userId){
        return ResponseEntity.ok(postService.likePost(postId, userId));
    }

    @DeleteMapping("/{postId}/user/{userId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId, @PathVariable long userId){
        String message = postService.deletePost(postId, userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
