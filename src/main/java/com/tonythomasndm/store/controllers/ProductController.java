package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.ProductDto;
import com.tonythomasndm.store.entities.Product;
import com.tonythomasndm.store.mappers.ProductMapper;
import com.tonythomasndm.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getProducts(
        @RequestParam(required = false, name= "categoryId") Byte categoryId
    ){
        return (
                (categoryId==null) ?
                        productRepository.findAll() :
                        productRepository.findAllByCategoryId(categoryId)
                ).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable("id") Long id
    ){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.ok(productMapper.toDto(product));
    }
}
// still not a n+1 bcoz we are eager laoding, but we cna iop[timize using join