package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Chat;
import com.vv.VisualVoyage.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUsersId(long userId);

    //SELECT c FROM Chat c WHERE :user IN elements(c.users) AND :reqUser IN elements(c.users) alternative query
    @Query("SELECT c FROM Chat c WHERE :user MEMBER of c.users AND :reqUser MEMBER of c.users")
    Chat findChatByUsersId(@Param("user")User user, @Param("reqUser") User reqUser);
}
