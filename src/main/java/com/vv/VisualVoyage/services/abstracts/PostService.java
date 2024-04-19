package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.requests.PostSaveDto;
import com.vv.VisualVoyage.dtos.responses.PostResponse;
import com.vv.VisualVoyage.entities.Post;

import java.util.List;

public interface PostService {

    PostResponse createNewPost(PostSaveDto postSaveDto, long userId);
    String deletePost(long postId, long userId);
    List<PostResponse> findPostsByUserId(long userId);
    PostResponse findPostById(long postId);
    List<PostResponse> findAllPosts();
    PostResponse savedPost(long postId, long userId);
    PostResponse likePost(long postId, long userId);

}
