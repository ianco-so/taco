package me.taco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import me.taco.model.TacoOrder;

@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public ModelAndView orderForm() {
        return new ModelAndView("OrderForm");
    }

    @PostMapping()
    public String processOrder(@RequestBody TacoOrder order, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }
}