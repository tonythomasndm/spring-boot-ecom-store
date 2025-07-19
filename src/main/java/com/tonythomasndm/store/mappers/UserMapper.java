package com.tonythomasndm.store.mappers;

import com.tonythomasndm.store.dtos.UserDto;
import com.tonythomasndm.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")// so that spring cna create beans at runtimme
public interface UserMapper {
    //@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);// mqpstruct will automatically implemen the code
}

// dont wrute the auto genrated code - dont touch it- mapstruct always regenrate if u change the srca dn target
