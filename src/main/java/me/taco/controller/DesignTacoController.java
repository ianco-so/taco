package me.taco.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showDesignForm() {
        var ingredients = createIngredietns();
        var types = Type.values();
        var mv = new ModelAndView("design");
        for (Type type : types) {
            mv.addObject(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        mv.addObject("tacoOrder", new TacoOrder());
        mv.addObject("taco", new Taco());
        return mv;
    }

    @PostMapping()
    public ModelAndView processDesign(Taco taco, @ModelAttribute("tacoOrder") TacoOrder tacoOrder) {
        log.info("Processing taco {}", taco);
        log.info("Processing tacoOrder {}", tacoOrder);
        ModelAndView mv = new ModelAndView("redirect:/orders/current");
        tacoOrder.addTaco(taco);
        return mv;
    }

    private List<Ingredient> createIngredietns() {
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
        return ingredients;
    }
    
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}
