package com.tonythomasndm.store.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserDto {// pascal naming
    //@JsonIgnore// applied at bother serializationa dn deserilaization
    //@JsonProperty("user_id")

    private Long id;
    private String name;
    private String email;
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private LocalDateTime createdAt;

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    //private String phoneNumber;
}
