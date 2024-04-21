package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.UserUpdateDto;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    private AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/users/all")
    public List<UserResponse> getAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable long id){
        return userService.findUserById(id);
    }
    @GetMapping("/users/search")
    public List<UserResponse> searchUser(@RequestParam("query") String query){
        return userService.searchUser(query);
    }

    @PutMapping("/api/users/")
    public UserResponse updateUser(@RequestBody UserUpdateDto userUpdateDto, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return userService.updateUser(userUpdateDto, requestUser.getId());
    }
    @PutMapping("/api/users/follow/{followId}")
    public String followUser(@RequestHeader("Authorization") String jwt, @PathVariable long followId){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return userService.followUser(requestUser.getId(), followId);
    }
}
