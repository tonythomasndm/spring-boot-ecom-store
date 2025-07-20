package com.tonythomasndm.store.mappers;

import com.tonythomasndm.store.dtos.CartDto;
import com.tonythomasndm.store.dtos.CartItemDto;
import com.tonythomasndm.store.entities.Cart;
import com.tonythomasndm.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    @Mapping(target="totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
