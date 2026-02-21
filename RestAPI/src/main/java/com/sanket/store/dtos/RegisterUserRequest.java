package com.sanket.store.dtos;


import lombok.Data;


//@Getter
//@Setter
@Data //It is the combination of both @Getter and @Setter
public class RegisterUserRequest {
    //this contains all the fields or data which we require to send request
    private String name;
    private String email;
    private String password;
    //we have not included id field,because it is not required or creating a new user.
}