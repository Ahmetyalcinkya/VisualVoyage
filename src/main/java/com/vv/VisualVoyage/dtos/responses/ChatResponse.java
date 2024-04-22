package com.vv.VisualVoyage.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {
    private Long id;
    private String chatName;
    private String chatImage;
    private LocalDateTime timestamp;
    private List<UserResponse> users;
}
