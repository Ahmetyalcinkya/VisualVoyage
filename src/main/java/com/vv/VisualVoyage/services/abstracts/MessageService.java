package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.requests.MessageRequest;
import com.vv.VisualVoyage.dtos.responses.ChatResponse;
import com.vv.VisualVoyage.dtos.responses.MessageResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;

import java.util.List;

public interface MessageService {

    MessageResponse createMessage(UserResponse requestUser, long chatId, MessageRequest messageRequest);
    List<MessageResponse> findChatMessages(long chatId);
}
