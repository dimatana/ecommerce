package com.training.ecommerce.controller;

import com.training.ecommerce.api.OrdersApi;
import com.training.ecommerce.model.OrderDto;
import com.training.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController implements OrdersApi {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(OrderDto orderDto) {
        OrderDto createdOrder = orderService.placeOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @Override
    public ResponseEntity<Void> deleteOrder(Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Override
    public ResponseEntity<OrderDto> getOrderById(Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Override
    public ResponseEntity<OrderDto> updateOrder(Long id, OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }
}
