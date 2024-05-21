package me.taco.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.taco.model.Ingredient;
import me.taco.model.Ingredient.Type;
import me.taco.model.Taco;
import me.taco.model.TacoOrder;

@Controller
@RequestMapping(path = "/design")
@SessionAttributes("tacoOrder")
@Slf4j
public class DesignTacoController {

    @GetMapping()
    public String showDesignForm() {
        return "design";
    }

    @PostMapping()
    public String processDesign(
        @Valid Taco taco, 
        Errors errors,
        @ModelAttribute("tacoOrder") TacoOrder tacoOrder
    ) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing taco {}", taco);
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }

    @ModelAttribute
    public void iToM(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla",Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef",   Type.PROTEIN),
            new Ingredient("CARN", "Carnitas",      Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes",Type.VEGGIES),
            new Ingredient("LETC", "Lettuce",       Type.VEGGIES),
            new Ingredient("CHED", "Cheddar",       Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack",Type.CHEESE),
            new Ingredient("SLSA", "Salsa",         Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream",    Type.SAUCE)
        );
        
        Type[] types = Ingredient.Type.values();

        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }
    
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
