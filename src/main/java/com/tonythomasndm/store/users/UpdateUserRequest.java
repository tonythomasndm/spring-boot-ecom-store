package com.tonythomasndm.store.users;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
