package me.taco.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @Table("Ingredient")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient implements Persistable<String> {

    @Size(max = 4, message = "Id must be at most 4 characters long")
    @Id
    private String id;

    @NotNull
    @Size(min = 3, max=25, message = "Name must be at least 3 characters long")
    // @Column("ingredient_name")
    private String ingredientName;

    @NotNull(message = "Type must not be null")
    @Size(max=10, message = "Type must be at most 10 characters long")
    // @Column("ingredient_type")
    private Type ingredientType;

    public static enum Type {
        WRAP, 
        PROTEIN, 
        VEGGIES, 
        CHEESE, 
        SAUCE
    }

    @Override
    public boolean isNew() {
        return true;
    }
}