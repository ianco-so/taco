package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import me.taco.model.TacoOrder;


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
        // ARRANGE
        String taco = " [\"FLTO\", \"GRBF\", \"LETC\"]";
        TacoOrder tacoOrder = new TacoOrder();
        // ACT & ASSERT
        this.mockMvc
            .perform(
                post("/design")
                .contentType(MediaType.TEXT_PLAIN)
                .content(taco)
                .sessionAttr("tacoOrder", tacoOrder)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/orders/current"));
    }
}
