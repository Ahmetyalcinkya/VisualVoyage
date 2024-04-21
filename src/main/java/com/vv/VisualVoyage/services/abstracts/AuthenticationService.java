package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.requests.LoginRequest;
import com.vv.VisualVoyage.dtos.requests.UserSaveDto;
import com.vv.VisualVoyage.dtos.responses.LoginResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;

public interface AuthenticationService {

    LoginResponse register(UserSaveDto userSaveDto);
    LoginResponse login(LoginRequest loginRequest);
    UserResponse findUserByJwt(String jwt);

}
