package com.tonythomasndm.store.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
