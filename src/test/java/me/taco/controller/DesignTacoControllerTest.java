package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import me.taco.api.model.Ingredient;
import me.taco.api.model.Ingredient.Type;
import me.taco.api.repository.IngredientRepository;


@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    private List<Ingredient> ingredients;

    @MockBean
    private IngredientRepository ingredientRepo;
    
    @BeforeEach
    public void setup() {
        this.ingredients = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        when(this.ingredientRepo.findAll())
            .thenReturn(this.ingredients);
    }
    
    @Test
    @WithMockUser(username="testuser", password="testpass")
    public void testShowDesignForm() throws Exception {
        this.mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(content().string(containsString("Design your taco!")))
            .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
            .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
            .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
            .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
            .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }

    @Test
    @WithMockUser(username="testuser", password="testpass", authorities = "ROLE_USER")
    public void testProcessDesignSucessfull() throws Exception {
        this.mockMvc
            .perform(
                post("/design").with(csrf())
                .param("tacoName", "Delicious Taco")
                .param("ingredients", "FLTO", "GRBF", "CHED")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/orders/current"));
    }

    @Test
    @WithMockUser(username="testuser", password="testpass", authorities = "ROLE_USER")
    public void testProcessDesignWithoutIngredient() throws Exception {
        this.mockMvc
            .perform(
                post("/design").with(csrf())
                .param("tacoName", "Delicious Taco")
            )
            .andExpect(status().isOk())
            .andExpect(view().name("design"));
    }

    @Test
    @WithMockUser(username="testuser", password="testpass", authorities = "ROLE_USER")
    public void testProcessDesignFailed() throws Exception {
        // Withcout name
        this.mockMvc
            .perform(
                post("/design").with(csrf())
                .param("ingredients", "FLTO", "GRBF", "CHED")
            )
            .andExpect(status().isOk())
            .andExpect(view().name("design"));
        
        // Without ingredients
        this.mockMvc
            .perform(
                post("/design").with(csrf())
                .param("tacoName", "Delicious Taco")
            )
            .andExpect(status().isOk())
            .andExpect(view().name("design"));
    }

}