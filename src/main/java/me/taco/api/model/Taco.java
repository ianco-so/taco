package me.taco.api.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "Taco")
/**
 * <h4> It's represents an Taco. </h4>
 * 
 * @author <a href="https://www.github.com/ianco-so">ianco</a>
 * @version 0.0.4
 * @since 0.0.1
 * @see Ingredient
 * @see TacoOrder
 */
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max=50, message = "Name must be between 3 and 50 characters long")
    private String tacoName;

    @NotNull
    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, max = 100, message = "choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    @Column(updatable = false)
    private final Date createdAt = new Date();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}