package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import me.taco.model.Ingredient;
import me.taco.model.Taco;
import me.taco.model.TacoOrder;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
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

        Taco taco2 = new Taco();
        taco2.setName("Taco 2");

        ingredient1 = new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
        ingredient2 = new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
        ingredient3 = new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);

        taco2.setIngredients(List.of(ingredient1, ingredient2, ingredient3));

        this.tacoOrder.addTaco(taco2);
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
    public void testProcessOrderWithValidOrder() throws Exception {
        // ARRANGE
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("clientName", "John Doe");
        params.add("street", "123 Main St");
        params.add("city", "Springfield");
        params.add("state", "RN");
        params.add("zip", "12345-678");
        params.add("ccNumber", "30569309025904");
        params.add("ccCVV", "123");
        params.add("ccExpiration", "12/24");

        // ACT & ASSERT
        this.mockMvc
            .perform(
                post("/orders")
                .params(params)
                .sessionAttr("tacoOrder", this.tacoOrder)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    public void testProcessOrderWithInvalidOrder () throws Exception {
        // ARRANGE
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("clientName", "John Doe");
        params.add("street", "123 Main St");
        params.add("city", "Springfield");
        params.add("state", "RN");
        params.add("zip", "2345-678");
        params.add("ccNumber", "30569309025905");
        params.add("ccCVV", "1234");
        params.add("ccExpiration", "12/21");

        // ACT & ASSERT
        this.mockMvc
            .perform(
                post("/orders")
                .params(params)
                .sessionAttr("tacoOrder", this.tacoOrder)
            )
            .andExpect(status().isOk())
            .andExpect(view().name("OrderForm"))
            .andExpect(content().string(containsString("Invalid ZIP code")))
            .andExpect(content().string(containsString("Not a valid credit card number")))
            .andExpect(content().string(containsString("Invalid CVV")))
            .andExpect(content().string(containsString("Must be formatted MM/YY")));
    }
}
