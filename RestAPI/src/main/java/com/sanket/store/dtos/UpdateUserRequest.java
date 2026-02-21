package com.sanket.store.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest
{
    //it contains the field which will be updated
    public String name;
    public String email;
}
