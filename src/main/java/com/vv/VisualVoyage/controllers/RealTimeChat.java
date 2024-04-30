package com.vv.VisualVoyage.controllers;


import com.vv.VisualVoyage.entities.Message;
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

    @MessageMapping("/chat/{groupId}")
    public Message sendToUser(@Payload Message message, @DestinationVariable String groupId ){

        simpMessagingTemplate.convertAndSendToUser(groupId,"/private", message);
        return message;
    }


}
