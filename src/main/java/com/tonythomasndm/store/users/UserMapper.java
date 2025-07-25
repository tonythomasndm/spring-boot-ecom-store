package com.tonythomasndm.store.users;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")// so that spring cna create beans at runtimme
public interface UserMapper {
    //@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);// mqpstruct will automatically implemen the code
    User toEntity(RegisterUserRequest request);
    void updateEntity(UpdateUserRequest request, @MappingTarget User user);
}

// dont wrute the auto genrated code - dont touch it- mapstruct always regenrate if u change the srca dn target
