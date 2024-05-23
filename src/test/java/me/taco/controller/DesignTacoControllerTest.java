package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import me.taco.model.TacoOrder;
import me.taco.repository.IngredientRepository;
import me.taco.repository.OrderRepository;


@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepo;

    @MockBean
    private OrderRepository orderRepo;

    private TacoOrder tacoOrder;

    @BeforeEach
    public void setup() {
        this.tacoOrder = new TacoOrder();
    }
    
    @Test
    public void testShowDesignForm() throws Exception {
        this.mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(content().string(containsString("Design your taco!")));
    }

    @Test
    public void testProcessDesignSucessfull() throws Exception {
        this.mockMvc
            .perform(
                post("/design")
                .param("tacoName", "Delicious Taco")
                .param("ingredients", "FLTO", "GRBF", "CHED")
                .sessionAttr("tacoOrder", tacoOrder)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/orders/current"));
    }

    @Test
    public void testProcessDesignWithoutName() throws Exception {
        this.mockMvc
        .perform(
            post("/design")
            .param("ingredients", "FLTO", "GRBF", "CHED")
            .sessionAttr("tacoOrder", tacoOrder)
        )
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(content().string(containsString("must not be null")));

    }

    @Test
    public void testProcessDesignWithInvalidName() throws Exception {
        this.mockMvc
            .perform(
                post("/design")
                .param("tacoName", "a")
                .param("ingredients", "FLTO", "GRBF", "CHED")
                .sessionAttr("tacoOrder", tacoOrder)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(content().string(containsString("between 3 and 50 char")));
    }

    @Test  
    public void testProcessDesignWithoutIngredients() throws Exception {
        this.mockMvc
            .perform(
                post("/design")
                .param("TacoName", "Delicious Taco")
                .sessionAttr("tacoOrder", tacoOrder)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(content().string(containsString("at least 1")));
    }

    @Test
    public void testProcessDesignWithInvalidIngredients() throws Exception {   
        this.mockMvc
            .perform(
                post("/design")
                .param("TacoName", "Delicious Taco")
                .param("ingredients", "")
                .sessionAttr("tacoOrder", tacoOrder)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(content().string(containsString("at least 1")));
    }
}
