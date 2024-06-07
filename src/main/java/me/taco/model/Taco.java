package me.taco.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// @Table("Taco")
public class Taco {

    @Id
    private Long id;

    @NotNull
    @Size(min = 3, max=50, message = "Name must be between 3 and 50 characters long")
    private String tacoName;

    @NotNull
    @Size(min = 1, max = 100, message = "choose at least 1 ingredient")
    private List<IngredientRef> ingredients = new ArrayList<>();

    private Date createdAt;
}