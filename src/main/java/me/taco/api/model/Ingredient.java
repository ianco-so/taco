package me.taco.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h4> It's represents an Ingredient. </h4>
 * <p>
 * The ingredient has to record information about its id, name and type.
 * </p>
 * 
 * @author <a href="https://www.github.com/ianco-so">ianco</a>
 * @version 0.0.4
 * @since 0.0.1
 * @see Taco
 */
@Data
@Entity(name = "Ingredient")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {

    @Id
    @Size(max = 4, message = "Id must be at most 4 characters long")
    private String id;

    @NotNull
    @Size(min = 3, max=25, message = "Name must be at least 3 characters long")
    private String ingredientName;

    @NotNull(message = "Type must not be null")
    private Type ingredientType;

    public static enum Type {
        WRAP, 
        PROTEIN, 
        VEGGIES, 
        CHEESE, 
        SAUCE
    }
}