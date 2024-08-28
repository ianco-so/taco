package me.taco.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import me.taco.api.model.Ingredient;
import me.taco.api.model.Taco;
import me.taco.api.model.TacoOrder;
import me.taco.api.model.Ingredient.Type;
import me.taco.api.repository.IngredientRepository;

@Controller
@RequestMapping(path = "/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @Autowired
    private IngredientRepository ingredientRepo;

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
            // log.error("Errors: {}", errors.getAllErrors());
            return "design";
        }
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }

    @ModelAttribute
    public void addIngredientsToModel (Model model) {
        var ingredients = ingredientRepo.findAll();
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
    
    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
            .filter(x -> x.getIngredientType().equals(type))
            .collect(Collectors.toList());
    }
}
