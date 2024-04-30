package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.CreateChatRequest;
import com.vv.VisualVoyage.dtos.responses.ChatResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.services.abstracts.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    private ChatService chatService;
    private AuthenticationService authenticationService;

    @Autowired
    public ChatController(ChatService chatService, AuthenticationService authenticationService) {
        this.chatService = chatService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/chats/")
    public ChatResponse createChat(@RequestHeader("Authorization") String jwt, @RequestBody CreateChatRequest createChatRequest){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return chatService.createChat(requestUser, createChatRequest.getMesUserId());
    }
    @GetMapping("/api/chats/all")
    public List<ChatResponse> getUsersChat(@RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return chatService.findUsersChat(requestUser.getId());
    }
    @GetMapping("/api/chats/{chatId}")
    public ChatResponse getUsersChatById(@RequestHeader("Authorization") String jwt, @PathVariable long chatId){
        authenticationService.findUserByJwt(jwt);
        return chatService.findChatById(chatId);
    }

}
