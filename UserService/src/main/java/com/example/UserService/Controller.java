package com.example.UserService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Users")
public class Controller {
    @GetMapping("/status")
    public String status() {
        return "User Service is up and running";
    }
}
