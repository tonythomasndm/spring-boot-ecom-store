package com.tonythomasndm.store.mappers;

import com.tonythomasndm.store.dtos.OrderDto;
import com.tonythomasndm.store.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
