package com.training.ecommerce.service;

import com.training.ecommerce.model.Order;
import com.training.ecommerce.model.Package;
import com.training.ecommerce.model.Product;
import com.training.ecommerce.repository.OrderRepository;
import com.training.ecommerce.repository.PackageRepository;
import com.training.ecommerce.repository.ProductRepository;
import com.training.ecommerce.repository.UserRepository;
import com.training.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PackageRepository packageRepository;

    public ProductService(PackageRepository packageRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.packageRepository = packageRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    //Create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    //get all products
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    //get a product by ID
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    //update a product
    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " +id));
        product.setName(productDetails.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());
        return productRepository.save(product);
    }
    //delete a product
    public void deleteProduct (Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found for id ::" + id));
        productRepository.delete(product);
    }
    //buy a product
    public Order buyProduct(Long productId, Long userId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found for this id ::" +productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id ::" +userId));

        Package aPackage = new Package();
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
    Optional<Package> packageOptional = packageRepository.findById(productId);
    if (packageOptional.isPresent()){
        Package aPackage = packageOptional.get();
        aPackage.setStatus("Returned");
        packageRepository.save(aPackage);
    }else {
        throw new ResourceNotFoundException("package not found for this id ::" + productId);
    }
    }
}
