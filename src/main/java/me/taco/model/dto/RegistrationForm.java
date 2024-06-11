package me.taco.model.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;
import me.taco.model.TacoUser;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
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
