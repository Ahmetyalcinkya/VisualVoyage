package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.LoginRequest;
import com.vv.VisualVoyage.dtos.requests.UserSaveDto;
import com.vv.VisualVoyage.dtos.responses.LoginResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public LoginResponse registerUser(@RequestBody UserSaveDto userSaveDto){
        return authenticationService.register(userSaveDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return authenticationService.login(loginRequest);
    }
}
