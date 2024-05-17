package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.taco.model.Ingredient;
import me.taco.model.Taco;
import me.taco.model.TacoOrder;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    private TacoOrder tacoOrder;

    @BeforeEach
    public void setup() {
        this.tacoOrder = new TacoOrder();

        Taco taco = new Taco();
        taco.setName("Taco 1");

        Ingredient ingredient1 = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
        Ingredient ingredient2 = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
        Ingredient ingredient3 = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);

        taco.setIngredients(List.of(ingredient1, ingredient2, ingredient3));

        this.tacoOrder.addTaco(taco);
    }

    @Test
    public void testOrderForm() throws Exception {
        this.mockMvc
            .perform(
                get("/orders/current")
                .sessionAttr("tacoOrder", this.tacoOrder)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("OrderForm"))
            .andExpect(content().string(containsString("Order your taco creations!")));
    }
    
    @Test
    public void testProcessOrder() throws Exception {
        // ARRANGE
        this.tacoOrder.setClientName("John Doe");
        this.tacoOrder.setStreet("1234 Endless Street");
        this.tacoOrder.setCity("Springfield");
        this.tacoOrder.setState("RN");
        this.tacoOrder.setZip("12345-678");
        this.tacoOrder.setCcNumber("1234567890123456");
        this.tacoOrder.setCcCVV("123");
        this.tacoOrder.setCcExpiration("01/23");


        String tacoOrderJson = this.objectMapper.writeValueAsString(this.tacoOrder);

        // ACT & ASSERT
        var result = this.mockMvc
            .perform(
                post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tacoOrderJson)
            )
            .andExpect(status().is3xxRedirection())
            .andReturn();

        var redirectUrl = result.getResponse().getRedirectedUrl();

        assertEquals("/", redirectUrl);
    }
}
