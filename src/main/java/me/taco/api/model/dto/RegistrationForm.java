package me.taco.api.model.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.taco.api.model.TacoUser;
import me.taco.api.model.enums.StateCode;
import me.taco.api.model.validation.ValidStateCode;

@Data
@Slf4j
public class RegistrationForm {
    private static final int MIN_STR_LEN = TacoUser.MIN_STR_LEN;
    private static final int MAX_STR_LEN = TacoUser.MAX_STR_LEN;

    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Username must be between "   +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-]*$", message = "Invalid username, use only alphanumeric characters and hyphens")
    private String username;
    
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Password must be between "   +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String password;
    
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Full Name must be between "  +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Invalid full name, use only alphabetic characters and spaces")
    private String fullname;
    
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Street must be between "     +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String street;
    
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "City must be between "       +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Invalid city, use only alphabetic characters and spaces")
    private String city;
    
    @ValidStateCode
    @Size(min = 2, max = 2, message = "State must be 2 characters long")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid state, use only alphabetic characters")
    private String state;

    @Size(min = 8, max = 10, message = "ZIP code must be 8 - 10 caracters long")
    @Pattern(regexp = "^[0-9-]+$", message = "Invalid zip, use only digits and hyphens")
    private String zip;
    @Size(min = 11, max = 17, message = "Phone number must be 11-17 caracters long")
    @Pattern(regexp = "^[0-9-()\\s]+$", message = "Invalid phone number, use only digits, hyphens, parentheses, and spaces")
    private String phone;
    
    public TacoUser toTacoUser(PasswordEncoder encoder) {
        return new TacoUser(
            this.username, 
            encoder.encode(this.password), 
            this.fullname, 
            this.street, 
            this.city,
            StateCode.valueOf(this.state.toUpperCase()),
            this.zip.replaceAll("[^0-9]", ""),
            this.phone.replaceAll("[^0-9]", "")
        );
    }
}