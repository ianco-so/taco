package me.taco.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {

    @Size(max = 4, message = "Id must be at most 4 characters long")
    private String id;

    @NotNull
    @Size(min = 3, max=25, message = "Name must be at least 3 characters long")
    private String ingredientName;

    @NotNull(message = "Type must not be null")
    @Size(max=10, message = "Type must be at most 10 characters long")
    private Type ingredientType;

    public static enum Type {
        WRAP, 
        PROTEIN, 
        VEGGIES, 
        CHEESE, 
        SAUCE
    }
}