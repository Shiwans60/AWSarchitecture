package com.example.OrderService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Orders")
public class Controller {
    @GetMapping("/status")
    public String status() {
        return "Order Service is up and running";
    }
}
