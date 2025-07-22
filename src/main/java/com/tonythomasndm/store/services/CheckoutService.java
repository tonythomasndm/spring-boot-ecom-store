package com.tonythomasndm.store.services;

import com.tonythomasndm.store.dtos.CheckoutRequest;
import com.tonythomasndm.store.dtos.CheckoutResponse;
import com.tonythomasndm.store.dtos.ErrorDto;
import com.tonythomasndm.store.entities.Order;
import com.tonythomasndm.store.exceptions.CartEmptyException;
import com.tonythomasndm.store.exceptions.CartNotFoundException;
import com.tonythomasndm.store.repositories.CartRepository;
import com.tonythomasndm.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()){
            throw new CartEmptyException();
        }
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return new CheckoutResponse(order.getId());

    }
}
