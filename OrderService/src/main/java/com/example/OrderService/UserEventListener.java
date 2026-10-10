package com.example.OrderService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventListener {

    private final CartRepo cartRepo;

    public UserEventListener(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @RabbitListener(queues = "user-queue")
    public void handleUserCreatedEvent(String message) {

        System.out.println("Received user-created event: " + message);

        if (message == null || !message.startsWith("userId=")) {
            throw new IllegalArgumentException(
                    "Invalid event format: " + message
            );
        }

        Long userId = Long.parseLong(
                message.substring("userId=".length()).trim()
        );

        if (cartRepo.findByUserId(userId).isPresent()) {
            System.out.println("Cart already exists for userId: " + userId);
            return;
        }

        Cart cart = new Cart(userId);
        Cart savedCart = cartRepo.save(cart);

        System.out.println(
                "Cart saved successfully for userId: "
                        + savedCart.getUserId()
                        + ", cart ID: " + savedCart.getId()
        );
    }
}