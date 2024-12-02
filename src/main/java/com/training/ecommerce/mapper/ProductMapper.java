package com.training.ecommerce.mapper;

import com.training.ecommerce.model.Product;
import com.training.ecommerce.model.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);

}
