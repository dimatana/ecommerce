package com.training.ecommerce.service;

import com.training.ecommerce.exception.ResourceNotFoundException;
import com.training.ecommerce.mapper.OrderMapper;
import com.training.ecommerce.model.*;
import com.training.ecommerce.model.Package;
import com.training.ecommerce.repository.OrderRepository;
import com.training.ecommerce.repository.PackageRepository;
import com.training.ecommerce.repository.ProductRepository;
import com.training.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderMapper orderMapper1) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper1;
    }

    //place a new order
    public OrderDto placeOrder (OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order createdOrder = orderRepository.save(order);
        return orderMapper.toDto(createdOrder);
    }
    //get all orders
    public List<OrderDto> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).toList();
    }
    //get an order by id
    public OrderDto getOrderById(Long id){
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("order no found for this id:: " + id));
    }
    //update order details
    public OrderDto updateOrder(Long id, OrderDto orderDetails){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("order not found for this id::" + id));
        order.setStatus(orderDetails.getStatus());
        order.setDate(orderDetails.getDate());
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }
    //delete an order
    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
//    //buy a product
//    public Order buyProduct(Long productId, Long userId){
//        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found for this id ::" +productId));
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +userId));
//
//        com.training.ecommerce.model.Package aPackage = new com.training.ecommerce.model.Package();
//        aPackage.setSender(user);
//        aPackage.setReceiver(user);
//        aPackage.setStatus("Pending");
//        aPackage.setDeliveryDate(LocalDate.now().plusDays(5));
//        packageRepository.save(aPackage);
//
//        Order order = new Order();
//        order.setUser(user);
//        order.setCourier(null);
//        order.setaPackage(aPackage);
//        order.setStatus("Order placed");
//        order.setDate(LocalDate.now());
//        return orderRepository.save(order);
//
//    }
//    //return a product
//    public void returnProduct (Long productId, Long userId){
//        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found for thi id ::" + productId));
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" + userId));
//        //logic to handle the product return
//        // for simplicity, we can set the package status to "Returned"
//        Optional<com.training.ecommerce.model.Package> packageOptional = packageRepository.findById(productId);
//        if (packageOptional.isPresent()){
//            Package aPackage = packageOptional.get();
//            aPackage.setStatus("Returned");
//            packageRepository.save(aPackage);
//        }else {
//            throw new ResourceNotFoundException("package not found for this id ::" + productId);
//        }
//    }
}
