package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.MessageRequest;
import com.vv.VisualVoyage.dtos.responses.MessageResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.Chat;
import com.vv.VisualVoyage.entities.Message;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.exceptions.VisualVoyageExceptions;
import com.vv.VisualVoyage.repositories.ChatRepository;
import com.vv.VisualVoyage.repositories.MessageRepository;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageManager implements MessageService {

    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private UserRepository userRepository;

    @Autowired
    public MessageManager(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageResponse createMessage(UserResponse requestUser, long chatId, MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new VisualVoyageExceptions("Chat not found!", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(requestUser.getId())
                .orElseThrow(() -> new VisualVoyageExceptions("User not found!", HttpStatus.NOT_FOUND));
        Message message = Message.builder()
                .chat(chat)
                .content(messageRequest.getContent())
                .image(messageRequest.getImage())
                .user(user)
                .timestamp(LocalDateTime.now())
                .build();
        Message saved = messageRepository.save(message);
        chat.getMessages().add(saved);
        chatRepository.save(chat);
        return MessageResponse.builder()
                .id(saved.getId())
                .content(saved.getContent())
                .image(saved.getImage())
                .timestamp(saved.getTimestamp())
                .userId(saved.getUser().getId())
                .chatId(saved.getChat().getId())
                .build();
    }

    @Override
    public List<MessageResponse> findChatMessages(long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new VisualVoyageExceptions("Chat not found!", HttpStatus.NOT_FOUND));
        List<Message> messages = messageRepository.findByChatId(chat.getId());

        return messages.stream().map(message -> MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .image(message.getImage())
                .timestamp(message.getTimestamp())
                .userId(message.getUser().getId())
                .chatId(message.getChat().getId())
                .build()).toList();
    }
}
