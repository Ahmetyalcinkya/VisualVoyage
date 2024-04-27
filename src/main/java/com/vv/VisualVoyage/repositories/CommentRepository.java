package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM social.comment AS c WHERE c.post_id = :postId ORDER BY c.created_at LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Comment> findPostsComments(long postId, int offset);
}
