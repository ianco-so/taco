package me.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.taco.api.controller.props.Props;
import me.taco.api.model.TacoOrder;
import me.taco.api.model.TacoUser;
import me.taco.api.repository.OrderRepository;
import me.taco.service.OrderService;

@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
@Slf4j
public class OrderController {

    @Autowired
    private Props props;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderService orderService;

    @GetMapping("/current")
    public String orderForm(Model model, @AuthenticationPrincipal TacoUser user) {
        var tacoOrder = (TacoOrder) model.getAttribute("tacoOrder");
        if (tacoOrder == null) {
            return "redirect:/design";
        }
        tacoOrder.setClientName(user.getFullname());
        tacoOrder.setClientStreet(user.getStreet());
        tacoOrder.setClientCity(user.getCity());
        tacoOrder.setClientState(user.getState().toString());
        tacoOrder.setZip(user.getZip());
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
        log.info("Order submitted: {}", order);
        order.setUser(user);
        var savedOrder = this.orderRepo.save(order);
        log.info("Order saved: {}", savedOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping("/delete/all")
    public String deleteAll() {
        this.orderService.deleteAllOrders();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUsers (@AuthenticationPrincipal TacoUser user, Model model) {
        var pageable = PageRequest.of(0, this.props.getPageSize());
        log.warn("User: {}", user);
        log.warn("Pageable {}", pageable);
        model.addAttribute("orders", this.orderRepo.findAllByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

}