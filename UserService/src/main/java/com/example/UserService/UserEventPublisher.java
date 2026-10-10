package com.example.UserService;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(Long userId) {
        String message = "userId=" + userId;

        rabbitTemplate.convertAndSend(
                "user-exchange",
                "user-routing",
                message
        );

        System.out.println("Published user-created event: " + message);
    }
}