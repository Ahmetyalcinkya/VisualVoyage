package com.vv.VisualVoyage.controllers;


import com.vv.VisualVoyage.dtos.requests.MessageRequest;
import com.vv.VisualVoyage.dtos.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RealTimeChat {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RealTimeChat(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/{groupId}")          // This can be replaced with message
    public MessageResponse sendToUser(@Payload MessageRequest messageRequest, @DestinationVariable String groupId ){

        simpMessagingTemplate.convertAndSendToUser(groupId,"/private", messageRequest);
        return MessageResponse.builder()
                .content(messageRequest.getContent())
                .image(messageRequest.getImage())
                .build();
    }


}
