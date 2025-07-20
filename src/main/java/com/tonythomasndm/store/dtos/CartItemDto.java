package com.tonythomasndm.store.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private CartProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
    // we will not use productDto bcoz it couples our implementataion to of shopping cart api to products api
}
