package com.training.ecommerce.mapper;

import com.training.ecommerce.model.Order;
import com.training.ecommerce.model.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}

