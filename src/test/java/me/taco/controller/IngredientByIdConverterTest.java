package me.taco.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.taco.api.model.Ingredient;
import me.taco.api.model.Ingredient.Type;
import me.taco.api.repository.IngredientRepository;

public class IngredientByIdConverterTest {
    
    private IngredientByIdConverter converter;


    @BeforeEach
    public void setup() {
        var ingredientRepo = mock(IngredientRepository.class);
        when(ingredientRepo.findById("AAAA"))
            .thenReturn(Optional.of(new Ingredient("AAAA", "Test Ingredient", Type.WRAP)));
        when(ingredientRepo.findById("ZZZZ"))
            .thenReturn(Optional.empty());
        this.converter = new IngredientByIdConverter(ingredientRepo);
    }

    @Test
    public void convertShouldReturnValueWhenPresent() {
        assertEquals(this.converter.convert("AAAA"), new Ingredient("AAAA", "Test Ingredient", Type.WRAP));
    }

    @Test
    public void convertShouldReturnNullWhenNotPresent() {
        assertThat(this.converter.convert("ZZZZ")).isNull();
    }
}
