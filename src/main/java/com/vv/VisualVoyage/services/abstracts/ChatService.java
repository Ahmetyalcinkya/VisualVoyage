package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.responses.ChatResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;

import java.util.List;

public interface ChatService {

    ChatResponse createChat(UserResponse requestUser, long messageUserId);
    ChatResponse findChatById(long chatId);
    List<ChatResponse> findUsersChat(long userId);
}
