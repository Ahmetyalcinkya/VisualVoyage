package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatId(long chatId);
}
