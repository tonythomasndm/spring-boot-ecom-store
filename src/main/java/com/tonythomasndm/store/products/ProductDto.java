package com.tonythomasndm.store.products;


import lombok.Data;

import java.math.BigDecimal;

@Data
//@AllArgsConstructor// u can use data instead for all
//@Getter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
}
