package com.vv.VisualVoyage.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse { //TODO UserId - Liked must be added here for the ui!
    private Long id;
    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;
    private Long userId;
    private List<UserResponse> liked;
}
