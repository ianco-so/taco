package me.taco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import me.taco.model.TacoOrder;

@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {
        return "OrderForm";
    }

    @PostMapping()
    public String processOrder(
        @Valid TacoOrder order, 
        Errors errors,
        SessionStatus sessionStatus
    ) {
        if (errors.hasErrors()) {
            return "OrderForm";
        }
        sessionStatus.setComplete();
        return "redirect:/";
    }
}