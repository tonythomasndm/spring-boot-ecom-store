package com.tonythomasndm.store.mappers;

import com.tonythomasndm.store.dtos.ProductDto;
import com.tonythomasndm.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target="categoryId", source="category.id")
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    @Mapping(target="id", ignore = true)
    void updateEntity(ProductDto productDto, @MappingTarget Product product);
}
