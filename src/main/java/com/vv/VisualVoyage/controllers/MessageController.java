package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.MessageRequest;
import com.vv.VisualVoyage.dtos.responses.MessageResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.services.abstracts.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    private MessageService messageService;
    private AuthenticationService authenticationService;

    @Autowired
    public MessageController(MessageService messageService, AuthenticationService authenticationService) {
        this.messageService = messageService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/message/chat/{chatId}")
    public MessageResponse createMessage(@RequestHeader("Authorization") String jwt, @PathVariable long chatId, @RequestBody MessageRequest request){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return messageService.createMessage(requestUser, chatId, request);
    }
    @GetMapping("/api/message/chat/{chatId}")
    public List<MessageResponse> chatMessages(@RequestHeader("Authorization") String jwt, @PathVariable long chatId){
        authenticationService.findUserByJwt(jwt);
        return messageService.findChatMessages(chatId);
    }
}
