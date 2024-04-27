package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.CommentSaveDto;
import com.vv.VisualVoyage.dtos.responses.CommentResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.services.abstracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private CommentService commentService;
    private AuthenticationService authenticationService;

    @Autowired
    public CommentController(CommentService commentService, AuthenticationService authenticationService) {
        this.commentService = commentService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/comments/post/{postId}")
    public CommentResponse createComment(@RequestBody CommentSaveDto commentSaveDto, @RequestHeader("Authorization") String jwt, @PathVariable long postId){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return commentService.createComment(commentSaveDto, postId, requestUser.getId());
    }
    @GetMapping("/comments/post/{postId}/{offset}")
    public List<CommentResponse> getPostsComments(@PathVariable long postId, @PathVariable int offset/*, @RequestHeader("Authorization") String jwt*/){
//        authenticationService.findUserByJwt(jwt);
        return commentService.findPostsComments(postId, offset);
    }
    @PutMapping("/api/comments/like/{commentId}")
    public CommentResponse likeComment(@PathVariable long commentId, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return commentService.likeComment(commentId, requestUser.getId());
    }
}
