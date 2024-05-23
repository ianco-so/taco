package me.taco.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;
import java.sql.ResultSet;

import me.taco.repository.IngredientRepository;
import me.taco.model.Ingredient;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class JdbcIngredientRepository  implements IngredientRepository{

    private JdbcTemplate jdbc;
    
    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("SELECT id, ingredient_name, ingredient_type FROM Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        var ingredient = jdbc.queryForObject(
            "SELECT id, ingredient_name, ingredient_type FROM Ingredient WHERE id=?", 
            this::mapRowToIngredient, 
            id
        );
        return Optional.ofNullable(ingredient);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
            "INSERT INTO Ingredient (id, ingredient_name, ingredient_type) VALUES (?, ?, ?)", 
            ingredient.getId(), 
            ingredient.getIngredientName(), 
            ingredient.getIngredientType().toString()
        );
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(rs.getString("id"), rs.getString("ingredient_name"), Ingredient.Type.valueOf(rs.getString("ingredient_type")));
    }
}
