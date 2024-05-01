package com.vv.VisualVoyage.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserSaveDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String username;
}
