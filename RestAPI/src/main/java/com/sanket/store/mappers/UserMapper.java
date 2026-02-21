package com.sanket.store.mappers;

import com.sanket.store.dtos.RegisterUserRequest;
import com.sanket.store.dtos.UpdateUserRequest;
import com.sanket.store.dtos.UserDto;
import com.sanket.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")//setting it as mapper interface defined in mapstruct and set the argument as spring, so that spring can create bean of this at runtime.
public interface UserMapper
{ //now, in this interface we can declare all kinds of mapping method
    UserDto toDto(User user); //mapping User to UserDto, here the name doesnt matters, what matters is the source and the target type

    User toEntity(RegisterUserRequest request); //mapping the new Dto ie. RegisterUserRequest with the User entity Object.

    //we will add a method for updating the existing user using Dto
    //In this method we dont need to return object because we are not creating any object

    void update(UpdateUserRequest request, @MappingTarget User user); //if we dont use @MappingTarget wwe will get comile time error
}
