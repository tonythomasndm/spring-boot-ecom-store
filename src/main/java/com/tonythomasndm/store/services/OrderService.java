package com.tonythomasndm.store.services;

import com.tonythomasndm.store.dtos.OrderDto;
import com.tonythomasndm.store.exceptions.OrderNotFoundException;
import com.tonythomasndm.store.mappers.OrderMapper;
import com.tonythomasndm.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }// collections be defaulta re mazy laoded

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You are not allowed to access this order!");
        }
        return orderMapper.toDto(order);
    }
}
