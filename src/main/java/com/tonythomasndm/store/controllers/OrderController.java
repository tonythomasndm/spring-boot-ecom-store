package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.ErrorDto;
import com.tonythomasndm.store.dtos.OrderDto;
import com.tonythomasndm.store.exceptions.OrderNotFoundException;
import com.tonythomasndm.store.mappers.OrderMapper;
import com.tonythomasndm.store.repositories.OrderRepository;
import com.tonythomasndm.store.services.AuthService;
import com.tonythomasndm.store.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleOrderNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(Exception e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                new ErrorDto(e.getMessage()));
    }
}
