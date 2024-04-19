package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user.id=:userId")
    List<Post> findPostsByUserId(long userId);
}
