package me.taco.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import me.taco.api.model.Taco;
import me.taco.api.model.TacoOrder;

public class TacoOrderTest {
    
    private TacoOrder tacoOrder;
    private Taco taco1;
    private Taco taco2;

    @BeforeEach
    public void setUp() {
        this.tacoOrder = new TacoOrder();
        this.tacoOrder.setTacos(new ArrayList<>());
        this.taco1 = new Taco();
        this.taco2 = new Taco();
    }

    @Test
    public void testAddTaco() {
        this.tacoOrder.addTaco(this.taco1);
        var tacos = this.tacoOrder.getTacos();
        assertEquals(1, tacos.size());
        assertTrue(tacos.contains(this.taco1));
    }

    @Test
    public void testRemoveTaco() {
        this.tacoOrder.addTaco(this.taco1);
        this.tacoOrder.addTaco(this.taco2);
        this.tacoOrder.removeTaco(this.taco1);
        var tacos = this.tacoOrder.getTacos();
        assertEquals(1, tacos.size());
        assertTrue(tacos.get(0) == this.taco2);
        assertFalse(tacos.get(0) == this.taco1);
    }

}
