package me.taco.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Ingredient {
    
    private final String id;

    @NotNull
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private final String name;

    @NotNull(message = "Type must not be null")
    private final Type type;

    public static enum Type {
        WRAP, 
        PROTEIN, 
        VEGGIES, 
        CHEESE, 
        SAUCE
    }
}