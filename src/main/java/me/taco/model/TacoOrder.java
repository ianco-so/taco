package me.taco.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * <h4> It's represents a Taco Order. </h4>
 * <p>
 *  The order has to record information about
 *  the client and the tacos that he/she wants.
 * </p>
 * @author <a href="https://www.github.com/ianco-so">ianco</a>
 * @version 0.0.2
 * @since 0.0.2
 * @see Taco
 */
@Data
public class TacoOrder {

    private String clientName;
    private String street;
    private String city;
    private String state;
    private String zip;
    
    private String ccNumber; /** cc = Credit Card */
    private String ccCVV;
    private String ccExpiration;

    private List<Taco> tacos = new ArrayList<>();

    /**
     * Add a taco to the order.
     * @param taco The taco to be added to the order.
     * @version 0.0.2
     * @since 0.0.2
     */
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    /**
     * Remove a taco from the order.
     * @param taco The taco to be removed from the order.
     * @version 0.0.2
     * @since 0.0.2
     */
    public void removeTaco(Taco taco) {
        this.tacos.remove(taco);
    }
}
