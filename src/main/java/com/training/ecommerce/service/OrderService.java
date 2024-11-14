package com.training.ecommerce.service;

import com.training.ecommerce.model.Order;
import com.training.ecommerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //place a new order
    public Order placeOrder (Order order) {
        return orderRepository.save(order);
    }
    //get all orders
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    //get an order by id
    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }
    //update order details
    public Order orderUpdate(Long id, Order orderDetails){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found for this id::" + id));
        order.setStatus(orderDetails.getStatus());
        order.setDate(orderDetails.getDate());
        return orderRepository.save(order);
    }
    //delete an order
    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found for this id :: " + id));
        orderRepository.delete(order);
    }
}
