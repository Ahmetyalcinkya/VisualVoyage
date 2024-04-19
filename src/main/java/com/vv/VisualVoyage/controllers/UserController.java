package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.UserSaveDto;
import com.vv.VisualVoyage.dtos.requests.UserUpdateDto;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public UserResponse registerUser(@RequestBody UserSaveDto userSaveDto){
        return userService.registerUser(userSaveDto);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable long id){
        return userService.findUserById(id);
    }
    @GetMapping("/search")
    public List<UserResponse> searchUser(@RequestParam("query") String query){
        return userService.searchUser(query);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable long id){
        return userService.updateUser(userUpdateDto,id);
    }
    @PutMapping("/follow/{reqId}/{followId}")
    public String followUser(@PathVariable long reqId, @PathVariable long followId){
        return userService.followUser(reqId,followId);
    }
}
