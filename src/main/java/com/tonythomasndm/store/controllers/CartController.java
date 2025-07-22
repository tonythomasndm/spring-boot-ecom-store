package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.*;
import com.tonythomasndm.store.exceptions.CartNotFoundException;
import com.tonythomasndm.store.exceptions.ProductNotFoundException;
import com.tonythomasndm.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/carts")// with restful apis we often use plural names
@Tag(name = "Carts")//previously it was cart-controller, now Carts
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the  Cart.")
    public ResponseEntity<CartItemDto> addToCarts(
            @Parameter(description = "The Id of the Cart. ")
            @PathVariable UUID cartId,
            @Valid @RequestBody AddItemToCartRequest request
    ) {
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(
            @PathVariable UUID cartId
    ) {
        return cartService.getCart(cartId);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ){
        return cartService.updateCartItem(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(
            @PathVariable(name="cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId
    ){
        cartService.deleteCartItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(
            @PathVariable UUID cartId
    ){
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }


    // we cna handle this logic at central place using an excpetion handler - prefereed to keep it at bottomm
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto("Cart not found.")
        );
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ErrorDto> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorDto("Product not found.")
        );
    }
}