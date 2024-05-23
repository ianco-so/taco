package me.taco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.taco.model.TacoOrder;
import me.taco.repository.OrderRepository;

@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private OrderRepository orderRepo;

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
            log.error("Errors: {}", errors.getAllErrors());
            return "OrderForm";
        }
        this.orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}