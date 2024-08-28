package me.taco.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import me.taco.api.model.Ingredient;
import me.taco.api.repository.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient>{
    
    private IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(@NonNull String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
