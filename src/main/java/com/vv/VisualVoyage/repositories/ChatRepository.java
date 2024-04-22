package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUsersId(long userId);
}
