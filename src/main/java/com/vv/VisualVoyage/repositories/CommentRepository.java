package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
