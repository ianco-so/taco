package me.taco.repository;

import java.util.Optional;

import me.taco.model.Ingredient;

public interface IngredientRepository {
    
    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);

}