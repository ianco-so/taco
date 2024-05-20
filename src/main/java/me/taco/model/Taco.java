package me.taco.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {

    @NotNull
    @Size(min = 3, max=50, message = "Name must be between 3 and 50 characters long")
    private String name;

    @NotNull
    @Size(min = 1, max = 100, message = "choose at least 1 ingredient")
    private List<Ingredient> ingredients;
}