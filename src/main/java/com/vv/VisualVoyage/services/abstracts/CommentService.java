package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.requests.CommentSaveDto;
import com.vv.VisualVoyage.dtos.responses.CommentResponse;

public interface CommentService {

    CommentResponse createComment(CommentSaveDto commentSaveDto, long postId, long userId);
    CommentResponse likeComment(long commentId, long userId);
    CommentResponse findCommentById(long commentId);
}
