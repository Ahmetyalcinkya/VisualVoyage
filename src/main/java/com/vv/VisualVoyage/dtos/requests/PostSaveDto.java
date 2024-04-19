package com.vv.VisualVoyage.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostSaveDto {
    private String caption;
    private String image;
    private String video;
}
