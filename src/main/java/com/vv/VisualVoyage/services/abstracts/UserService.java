package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.requests.UserUpdateDto;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.entities.User;

import java.util.List;

public interface UserService {

    UserResponse findUserById(long id);
    UserResponse findUserByEmail(String email);
    String followUser(long reqUserId, long followUserId);
    List<UserResponse> searchUser(String query);
    UserResponse updateUser(UserUpdateDto userUpdateDto, long userId);
}
