package com.example.OrderService;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    @Indexed(unique = true)
    private Long userId;

    private List<String> items = new ArrayList<>();

    private Instant createdAt = Instant.now();

    public Cart() {
    }

    public Cart(Long userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<String> getItems() {
        return items;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
