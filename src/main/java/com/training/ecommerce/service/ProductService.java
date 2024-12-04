package com.training.ecommerce.service;

import com.training.ecommerce.exception.ResourceNotFoundException;
import com.training.ecommerce.mapper.ProductMapper;
import com.training.ecommerce.model.*;
import com.training.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;

}
    @Transactional
    //Create a new product
    public ProductDto createProduct(ProductDto productDto) {
        final Product product = productMapper.toEntity(productDto);
        Product createdProduct = productRepository.save(product);
        return productMapper.toDto(createdProduct);
    }
    //get all products
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }
    //get a product by ID
    public ProductDto getProductById(Long id){
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product with this id not found::" + id));
    }
    //update a product
    public ProductDto updateProduct(Long id, ProductDto productDetails){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + id));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }
    //delete a product
    public void deleteProduct (Long id){
        productRepository.deleteById(id);
    }

}
