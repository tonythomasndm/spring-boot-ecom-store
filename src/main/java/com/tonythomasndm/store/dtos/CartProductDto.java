package com.tonythomasndm.store.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CartProductDto {
    private Long id;
    private String name;
    private String price;
}
