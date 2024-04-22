package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.responses.ChatResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.Chat;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.repositories.ChatRepository;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ChatManager implements ChatService {

    private ChatRepository chatRepository;
    private UserRepository userRepository;

    @Autowired
    public ChatManager(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ChatResponse createChat(UserResponse requestUser, UserResponse messageUser) {

        User reqUser = userRepository.findById(requestUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!
                User mesUser = userRepository.findById(messageUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!

        Chat isExist = chatRepository.findChatByUsersId(mesUser, reqUser);

        if(isExist != null){
            return ChatResponse.builder()
                    .id(isExist.getId())
                    .chatName(isExist.getChatName())
                    .chatImage(isExist.getChatImage())
                    .timestamp(isExist.getTimestamp())
                    .users(isExist.getUsers().stream().map(user -> UserResponse.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .gender(user.getGender())
                            .build()).toList())
                    .build();
        }
        Chat chat = Chat.builder()
                .timestamp(LocalDateTime.now())
                .build();
        chat.getUsers().add(mesUser);
        chat.getUsers().add(reqUser);
        Chat saved = chatRepository.save(chat);

        return ChatResponse.builder()
                .id(saved.getId())
                .chatName(saved.getChatName())
                .chatImage(saved.getChatImage())
                .timestamp(saved.getTimestamp())
                .users(saved.getUsers().stream().map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .build()).toList())
                .build();
    }

    @Override
    public ChatResponse findChatById(long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found with the given id!")); //TODO Throw exception!

        return ChatResponse.builder()
                .id(chat.getId())
                .chatName(chat.getChatName())
                .chatImage(chat.getChatImage())
                .timestamp(chat.getTimestamp())
                .users(chat.getUsers().stream().map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .build()).toList())
                .build();
    }

    @Override
    public List<ChatResponse> findUsersChat(long userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!
        List<Chat> chats = chatRepository.findByUsersId(existUser.getId());

        return chats.stream().map(chat -> ChatResponse.builder()
                .id(chat.getId())
                .chatName(chat.getChatName())
                .chatImage(chat.getChatImage())
                .timestamp(chat.getTimestamp())
                .users(chat.getUsers().stream().map(user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .gender(user.getGender())
                        .build()).toList())
                .build()).toList();
    }
}
