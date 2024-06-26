package com.vv.VisualVoyage.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String profilePicture;
    private String coverPicture;
    private String username;
    private String biography;
    private Set<Long> followers;
    private Set<Long> followings;
    private List<PostResponse> savedPosts;
}
