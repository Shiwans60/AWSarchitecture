package com.example.OrderService;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepo extends MongoRepository<Cart,String> {
    Optional<Cart> findByUserId(Long userId);
}
