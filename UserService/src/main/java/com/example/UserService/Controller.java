package com.example.UserService;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/Users")
public class Controller {
    private final UserRepo userRepo;
    private final UserEventPublisher userEventPublisher;
    public Controller(UserRepo userRepo, UserEventPublisher userEventPublisher) {
        this.userRepo = userRepo;
        this.userEventPublisher = userEventPublisher;
    }


    @GetMapping("/status")
    public String status() {
        return "User Service is up and running";
    }

    @PostMapping("/create")
    public  User createUser(@RequestBody User user) {
        User userCreated = userRepo.save(user);
        try {
            userEventPublisher.publishUserCreated(userCreated.getId());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User saved but event publisher failed");
        }
        return userCreated;
    }
}
