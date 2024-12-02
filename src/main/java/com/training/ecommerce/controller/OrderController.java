package com.training.ecommerce.controller;

import com.training.ecommerce.service.OrderService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

}
