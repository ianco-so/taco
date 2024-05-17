package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowDesignForm() throws Exception {
        this.mockMvc.perform(get("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("design"))
            .andExpect(content().string(containsString("Design your taco!")));
    }

    @Test
    public void testProcessDesign() throws Exception {
        this.mockMvc.perform(put("/design"))
            .andExpect(status().isOk())
            .andExpect(view().name("redirect:/orders/current"))
            .andExpect(content().string(containsString("Order your taco creations!")));
    }
}
