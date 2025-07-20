package com.tonythomasndm.store.dtos;

import com.tonythomasndm.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data// cpbination fo getter, setter, tostring, ahscoide - amke sa the code little bit more cleaner
// so that spring can intialize etc
// request model alignment
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max=255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Lowercase(message = "Email must be lowercase")
    private String email;

    @NotBlank(message ="Password is required")
    @Size(min=6, max=25, message = "Password must be between 6 to 25 characters long")
    private String password;


}

// no busisness rules or logic cna be implementd in validation - cutom validation
// bcoiz these are traiggered everytime we make request , if some field is invalid, we sont wnana waste tiem querying teh datbase
// so fgirst we vaidate the input-> then bussines rules validation
