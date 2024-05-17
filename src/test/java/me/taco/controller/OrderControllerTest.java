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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        String tacoOrderJson = this.objectMapper.writeValueAsString(this.tacoOrder);
        // ACT & ASSERT
        this.mockMvc
            .perform(
                post("/orders")
                .contentType("application/json")
                .content(tacoOrderJson)
            )
            .andExpect(status().is3xxRedirection());
    }
}
