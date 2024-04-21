package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Reel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {

    @Query("SELECT r FROM Reel r WHERE r.user.id=:userId")
    List<Reel> findReelsByUserId(long userId);
}
