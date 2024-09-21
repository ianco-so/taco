package me.taco.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import me.taco.api.controller.props.Props;
import me.taco.api.model.Ingredient;
import me.taco.api.model.Ingredient.Type;
import me.taco.api.model.Taco;
import me.taco.api.model.TacoOrder;
import me.taco.api.repository.IngredientRepository;
import me.taco.api.repository.OrderRepository;
import me.taco.service.OrderService;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    private TacoOrder tacoOrder;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepo;

    @MockBean
    private IngredientRepository ingredientRepo;

    @MockBean
    private Props props;

    @BeforeEach
    public void setup() {
        this.tacoOrder = new TacoOrder();

        Taco taco = new Taco();
        taco.setTacoName("Taco 1");
        
        Ingredient i1 = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
        Ingredient i2 = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
        Ingredient i3 = new Ingredient("CHED", "Cheddar", Type.CHEESE);

        taco.setIngredients(List.of(i1, i2, i3));

        this.tacoOrder.addTaco(taco);

        Taco taco2 = new Taco();
        taco2.setTacoName("Taco 2");

        i1 = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
        i2 = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
        i3 = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);

        taco2.setIngredients(List.of(i1, i2, i3));

        this.tacoOrder.addTaco(taco2);
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
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
     @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testProcessOrderWithValidOrder() throws Exception {
        // ARRANGE
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("clientName", "John Doe");
        params.add("clientStreet", "123 Main St");
        params.add("clientCity", "Springfield");
        params.add("clientState", "RN");
        params.add("zip", "12345-678");
        params.add("ccNumber", "30569309025904");
        params.add("ccCvv", "123");
        params.add("ccExpiration", "12/24");

        // ACT & ASSERT
        this.mockMvc
            .perform(
                post("/orders").with(csrf())
                .params(params)
                .sessionAttr("tacoOrder", this.tacoOrder)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/"));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword", roles = "USER")
    public void testProcessOrderWithInvalidOrder () throws Exception {
        // ARRANGE
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("clientName", "John Doe");
        params.add("clientStreet", "123 Main St");
        params.add("clientCity", "Springfield");
        params.add("clientState", "RN");
        params.add("zip", "2345-678");
        params.add("ccNumber", "30569309025905");
        params.add("ccCvv", "1234");
        params.add("ccExpiration", "12/21");

        // ACT & ASSERT
        this.mockMvc
            .perform(
                post("/orders").with(csrf())
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
