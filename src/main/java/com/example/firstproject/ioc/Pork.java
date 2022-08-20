package com.example.firstproject.ioc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class Pork extends Ingredient {
    public Pork(String name) {
        super(name);
    }
}
