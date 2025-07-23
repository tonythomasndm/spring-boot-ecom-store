package com.tonythomasndm.store.carts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull(message = "Quantity must be provided.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    @Max(value = 1000, message = "Quantity must be less than or equal to 1000.")
    private Integer quantity;
}
// why not int - bcoz null doenst work, more logic or impelmentataion cannot be explred - diff scenarios - expliciyt erroe messages
// particually handle nulkkl fields - then use wra[pper type