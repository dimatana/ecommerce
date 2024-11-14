package com.training.ecommerce.controller;

import com.training.ecommerce.model.Order;
import com.training.ecommerce.model.Product;
import com.training.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/buy")
    public Order buyProduct(@RequestBody BuyRequest buyRequest){
        return productService.buyProduct(buyRequest.getProductId(), buyRequest.getUserId());
    }
    @PostMapping("/return")
    public void returnProduct(@RequestBody ReturnRequest returnRequest){
        productService.returnProduct(returnRequest.getProductId(), returnRequest.getUserId());
    }
    @PostMapping("create")
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
    @GetMapping("/update/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
        return productService.updateProduct(id, productDetails);
    }
    @GetMapping("/delete/{id}")
    public void  deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    // dto to capture buy request
     public static class BuyRequest {
        private Long productId;
        private Long userId;
        //getters and setters

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }
    }
     //DTO to capture return request
     public static class ReturnRequest{
            private Long productId;
            private Long userId;

         public Long getProductId() {
             return productId;
         }

         public void setProductId(Long productId) {
             this.productId = productId;
         }

         public Long getUserId() {
             return userId;
         }

         public void setUserId(Long userId) {
             this.userId = userId;
         }
     }
    }


