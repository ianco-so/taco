package me.taco.core.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import me.taco.api.repository.IngredientRepository;
import me.taco.api.repository.OrderRepository;
import me.taco.api.repository.TacoRepository;
import me.taco.api.repository.TacoUserRepository;
import me.taco.controller.props.Props;
import me.taco.service.OrderService;
@WebMvcTest
// @Disabled("I'm not sure why this test is failing. I'm going to disable it for now.")
public class WebConfigTest {
    @Autowired
    private MockMvc mockMvc;

    
    // Note: Most of these mocks are here to avoid autowiring issues. They aren't
    //       actually used in the course of the home page test, so their behavior
    //       isn't important. They just need to exist so autowiring can take place.
    
    @MockBean
    private IngredientRepository ingredientRepo;
    
    @MockBean
    private OrderRepository orderRepo;
    
    @MockBean
    private TacoUserRepository userRepo;
    
    @MockBean
    private OrderService orderService;
    
    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private Props props;

    @MockBean
    private TacoRepository tacoRepo;

    @Test
    @WithMockUser
    public void testGetHome() throws Exception {
        // Add test code here
        this.mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(content().string(containsString("Welcome to Taco")));
    }
}