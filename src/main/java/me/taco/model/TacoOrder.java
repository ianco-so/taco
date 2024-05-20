package me.taco.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name is required")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String clientName;
    private String street;
    private String city;
    private String state;
    
    @Pattern(regexp = "^[0-9]{5}$", message = "Invalid ZIP code")
    private String zip;
    
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber; /** cc = Credit Card */

    @Pattern(regexp = "^[0-9]{3}$", message = "Invalid CVV")
    private String ccCVV;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @NotNull(message = "You must choose at least 1 taco")
    @Size(min = 1, message = "You must choose at least 1 taco")
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
