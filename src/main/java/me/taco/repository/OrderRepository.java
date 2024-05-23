package me.taco.repository;

import me.taco.model.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);
    
}
