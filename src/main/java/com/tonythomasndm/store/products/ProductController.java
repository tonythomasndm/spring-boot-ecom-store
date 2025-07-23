package com.tonythomasndm.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,// saME - DONT CREATE SEPARTE DTPS FOR DRUD
            UriComponentsBuilder uriBuilder
    ){
        if(productDto == null || productDto.getName().isEmpty() || productDto.getCategoryId() == null || productDto.getPrice()==null){
            return ResponseEntity.badRequest().build();
        }
        else{

            var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
            if(category == null){
                return ResponseEntity.badRequest().build();
            }

            var product = productMapper.toEntity(productDto);
            product.setCategory(category);
            productRepository.save(product);
            productDto.setId(product.getId());

            var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();

            return ResponseEntity.created(uri).body(productDto);

            //return ResponseEntity.created(uri).body(productMapper.toDto(product));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    ){
//        if(productDto == null || productDto.getName().isEmpty() || productDto.getCategoryId() == null){
//            return ResponseEntity.badRequest().build();
//        }else{
            var product = productRepository.findById(id).orElse(null);
            if(product == null){
                return ResponseEntity.notFound().build();
            }else{
                var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
                if(category == null){
                    return ResponseEntity.badRequest().build();
                }
               productMapper.updateEntity(productDto, product);
                product.setCategory(category);
               productRepository.save(product);
               productDto.setId(product.getId());
               return ResponseEntity.ok(productDto);
            }
    //}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ){
        var product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }else{
            productRepository.delete(product);
            return ResponseEntity.noContent().build();
        }
    }
}
// still not a n+1 bcoz we are eager laoding, but we cna iop[timize using join