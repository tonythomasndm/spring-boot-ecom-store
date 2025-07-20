package com.tonythomasndm.store.services;

import com.tonythomasndm.store.dtos.CartDto;
import com.tonythomasndm.store.dtos.CartItemDto;
import com.tonythomasndm.store.entities.Cart;
import com.tonythomasndm.store.exceptions.CartNotFoundException;
import com.tonythomasndm.store.exceptions.ProductNotFoundException;
import com.tonythomasndm.store.mappers.CartMapper;
import com.tonythomasndm.store.repositories.CartRepository;
import com.tonythomasndm.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto getCart( UUID cartId, Long productId ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();

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
    }
}
