package com.vv.VisualVoyage.controllers;

import com.vv.VisualVoyage.dtos.requests.ReelSaveDto;
import com.vv.VisualVoyage.dtos.responses.ReelResponse;
import com.vv.VisualVoyage.dtos.responses.UserResponse;
import com.vv.VisualVoyage.services.abstracts.AuthenticationService;
import com.vv.VisualVoyage.services.abstracts.ReelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelController {

    private ReelService reelService;
    private AuthenticationService authenticationService;

    @Autowired
    public ReelController(ReelService reelService, AuthenticationService authenticationService) {
        this.reelService = reelService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/reels/")
    public ReelResponse createReel(@RequestBody ReelSaveDto reelSaveDto, @RequestHeader("Authorization") String jwt){
        UserResponse requestUser = authenticationService.findUserByJwt(jwt);
        return reelService.createReel(reelSaveDto, requestUser.getId());
    }
    @GetMapping("/api/reels/all")
    public List<ReelResponse> getAllReels(@RequestHeader("Authorization") String jwt){
        authenticationService.findUserByJwt(jwt);
        return reelService.findAllReels();
    }
    @GetMapping("/api/reels/user/{userId}")
    public List<ReelResponse> getReelsByUser(@RequestHeader("Authorization") String jwt,@PathVariable long userId){
        authenticationService.findUserByJwt(jwt);
        return reelService.findReelsByUser(userId);
    }
}
