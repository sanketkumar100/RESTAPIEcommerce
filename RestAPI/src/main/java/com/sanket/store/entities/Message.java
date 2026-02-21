package com.sanket.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter  //we can manually create constructor and getters but, in our project we are using LOMBOK
public class Message
{
    private String text;
}
