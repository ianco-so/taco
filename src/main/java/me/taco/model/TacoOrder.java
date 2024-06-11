package me.taco.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
 * @version 0.0.4
 * @since 0.0.1
 * @see Taco
 */
@Data
@Entity(name = "TacoOrder")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 50, message = "Name must be at least 5 characters long")
    private String clientName;
    
    @NotBlank(message = "Street is required")
    private String clientStreet;
    
    @NotBlank(message = "City is required")
    private String clientCity;
    
    @NotBlank(message = "State is required")
    private String clientState;
    
    @NotBlank(message = "ZIP code is required")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "Invalid ZIP code")
    private String zip;
    
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber; /** cc = Credit Card */
    
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][4-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Pattern(regexp = "^[0-9]{3}$", message = "Invalid CVV")
    private String ccCvv;
    
    private Date placedAt = new Date();
    
    @NotNull(message = "You must choose at least 1 taco")
    @Size(min = 1, message = "You must choose at least 1 taco")
    @OneToMany(targetEntity = Taco.class, cascade = CascadeType.ALL)
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne
    private TacoUser user;
    

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
