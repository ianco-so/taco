package me.taco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import me.taco.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        this.orderRepo.deleteAll();
    }
}
