package me.taco.api.model.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Size;
import lombok.Data;
import me.taco.api.model.TacoUser;

@Data
public class RegistrationForm {
    private static final int MIN_STR_LEN = TacoUser.MIN_STR_LEN;
    private static final int MAX_STR_LEN = TacoUser.MAX_STR_LEN;

    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Username must be between "   +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String username;
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Password must be between "   +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String password;
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Full Name must be between "  +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String fullname;
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Street must be between "     +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String street;
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "City must be between "       +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String city;
    @Size(min = 2, max = 2, message = "State must be 2 characters long")
    private String state;
    @Size(min = 8, max = 10, message = "ZIP code must be 8 - 10 digits")
    private String zip;
    @Size(min = 11, max = 16, message = "Phone number must be 11-16 digits")
    private String phone;
    
    public TacoUser toTacoUser(PasswordEncoder encoder) {
        return new TacoUser(
            this.username, 
            encoder.encode(this.password), 
            this.fullname, 
            this.street, 
            this.city, 
            this.state, 
            this.zip, 
            this.phone
        );
    }

}
