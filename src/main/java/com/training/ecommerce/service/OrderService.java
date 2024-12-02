package com.training.ecommerce.service;

import com.training.ecommerce.exception.ResourceNotFoundException;
import com.training.ecommerce.model.Order;
import com.training.ecommerce.model.Package;
import com.training.ecommerce.model.Product;
import com.training.ecommerce.model.User;
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
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, PackageRepository packageRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
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
    //buy a product
    public Order buyProduct(Long productId, Long userId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found for this id ::" +productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +userId));

        com.training.ecommerce.model.Package aPackage = new com.training.ecommerce.model.Package();
        aPackage.setSender(user);
        aPackage.setReceiver(user);
        aPackage.setStatus("Pending");
        aPackage.setDeliveryDate(LocalDate.now().plusDays(5));
        packageRepository.save(aPackage);

        Order order = new Order();
        order.setUser(user);
        order.setCourier(null);
        order.setaPackage(aPackage);
        order.setStatus("Order placed");
        order.setDate(LocalDate.now());
        return orderRepository.save(order);

    }
    //return a product
    public void returnProduct (Long productId, Long userId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found for thi id ::" + productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" + userId));
        //logic to handle the product return
        // for simplicity, we can set the package status to "Returned"
        Optional<com.training.ecommerce.model.Package> packageOptional = packageRepository.findById(productId);
        if (packageOptional.isPresent()){
            Package aPackage = packageOptional.get();
            aPackage.setStatus("Returned");
            packageRepository.save(aPackage);
        }else {
            throw new ResourceNotFoundException("package not found for this id ::" + productId);
        }
    }
}
