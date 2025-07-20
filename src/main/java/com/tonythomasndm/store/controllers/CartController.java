package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.AddItemToCartRequest;
import com.tonythomasndm.store.dtos.CartDto;
import com.tonythomasndm.store.dtos.CartItemDto;
import com.tonythomasndm.store.dtos.UpdateCartItemRequest;
import com.tonythomasndm.store.entities.Cart;
import com.tonythomasndm.store.entities.CartItem;
import com.tonythomasndm.store.mappers.CartMapper;
import com.tonythomasndm.store.repositories.CartRepository;
import com.tonythomasndm.store.repositories.ProductRepository;
import com.tonythomasndm.store.services.CartService;
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
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        var cartDto = cartService.createCart();

        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
//        // why not builder one? bcoz it expects a uri - useful for public apis
//        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addToCarts(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddItemToCartRequest request) {

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        // this approach throws an excpetion
//        var cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", "product does not exist in the system")
            );
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();// USER MADE MISTAKE HERE
        }
        // entities w/o function(behavior) or responsibility - anemic domain
        // oop - combine rleated object and behavior
        var cartItem = cart.addItemToCart(product);// more object oriented domain model
//
//        var cartItem = cart.getItemByProductId(product.getId());
//
//        if(cartItem != null) {
//            cartItem.setQuantity(cartItem.getQuantity() + 1);
//        } else{
//            cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(1);
//            cartItem.setCart(cart);
//            cart.getItems().add(cartItem);
//            // we have two options to save it - one is to craete a differnet cartitemrepostyory or else save it in existing cartrepo
//            // we choose second method bcoz cartitem dont have a indpendnet lifecycle - persistent throigh cart design
//            // aggregrate root -> cart
//            // ddd - domain driven design
//        }

        cartRepository.save(cart);
        var cartItemDto = cartMapper.toDto(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(
            @PathVariable UUID cartId
    ) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toDto(cart));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart not found.")
            );
            // this doenst have a bpody to inform
            //return ResponseEntity.notFound().build();
        }
        var cartItem  = cart.getItemByProductId(productId);
        if (cartItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Product was not found in the cart.")
            );
        }//we dont have to search the product repository and check it with cartId

        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteProductFromCart(
            @PathVariable(name="cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart was not found.")
            );
        }
//        var cartItem  = cart.getItemByProductId(productId);
//        if (cartItem != null) {
//            cart.getItems().remove(cartItem);
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
////                    Map.of("error", "Product was not found in the cart.")
////            );
//            // we dont need to be alwasy aggressive
//        }

        cart.removeItemFromCart(productId);
        cartRepository.save(cart);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(
            @PathVariable UUID cartId
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Cart was not found.")
            );
        }
        cart.clear();
        cartRepository.save(cart);
        return ResponseEntity.noContent().build();
    }
    // if we delete a crt, then the client ahs to sned another request to create a new cart
    // which is unnecaary




}
// in restful apis ist good to have this hearcrhy inouir resources