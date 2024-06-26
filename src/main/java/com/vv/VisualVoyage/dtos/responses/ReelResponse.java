package com.vv.VisualVoyage.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReelResponse {
    private Long id;
    private String title;
    private String video;
    private LocalDateTime createdAt;
    private Long userId;
}
