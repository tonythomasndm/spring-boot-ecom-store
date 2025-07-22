package com.tonythomasndm.store.controllers;

import com.tonythomasndm.store.dtos.CheckoutRequest;
import com.tonythomasndm.store.dtos.CheckoutResponse;
import com.tonythomasndm.store.dtos.ErrorDto;
import com.tonythomasndm.store.exceptions.CartEmptyException;
import com.tonythomasndm.store.exceptions.CartNotFoundException;
import com.tonythomasndm.store.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout( @Valid @RequestBody CheckoutRequest request){
        return checkoutService.checkout(request);
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleExceptions(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
