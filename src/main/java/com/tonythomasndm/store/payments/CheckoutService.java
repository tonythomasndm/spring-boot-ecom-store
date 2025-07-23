package com.tonythomasndm.store.payments;

import com.tonythomasndm.store.orders.Order;
import com.tonythomasndm.store.carts.CartEmptyException;
import com.tonythomasndm.store.carts.CartNotFoundException;
import com.tonythomasndm.store.carts.CartRepository;
import com.tonythomasndm.store.orders.OrderRepository;
import com.tonythomasndm.store.auth.AuthService;
import com.tonythomasndm.store.carts.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor// only final variables will be intialized
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional// atomic operatiuon
    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()){
            throw new CartEmptyException();
        }
        var order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        // create a checkout session
        try{
            var checkoutSession = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());
            return new CheckoutResponse(order.getId(), checkoutSession.getCheckoutUrl());
        } catch(PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway
            .parseWebhookRequest(request)
            .ifPresent(paymentResult -> {
                var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                order.setStatus(paymentResult.getPaymentStatus());
                orderRepository.save(order);
            });
    }
}
// FEWER PARMATERS ARE BETTER THAN MANY PARAMTERS
