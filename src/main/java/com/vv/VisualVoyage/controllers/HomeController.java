package com.vv.VisualVoyage.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String homeControllerHandler(){
        return "This is home Controller!";
    }
}
