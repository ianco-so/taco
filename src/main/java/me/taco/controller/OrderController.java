package me.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.taco.model.TacoOrder;
import me.taco.model.TacoUser;
import me.taco.repository.OrderRepository;

@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
@Slf4j
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/current")
    public String orderForm() {
        return "OrderForm";
    }

    @PostMapping()
    public String processOrder(
        @Valid TacoOrder order, 
        Errors errors,
        SessionStatus sessionStatus,
        @AuthenticationPrincipal TacoUser user
    ) {
        if (errors.hasErrors()) {
            log.error("Errors: {}", errors.getAllErrors());
            return "OrderForm";
        }
        
        order.setUser(user);

        this.orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}