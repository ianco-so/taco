package me.taco.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class TacoUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    // private static final short MIN_STR_LEN = 3;
    // private static final short MAX_STR_LEN = 50;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    // @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Fullname must be between "+MIN_STR_LEN+" and "+MAX_STR_LEN+"characters long")
    private final String fullname;

    // @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Street must be between "+MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private final String street;

    // @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "City must be between "+MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private final String city;

    // @Size(min = 2, max = 2, message = "State must be 2 characters long")
    private final String state;

    // @Size(min = 8, max = 10, message = "ZIP code must be 8 - 10 digits")
    // @Digits(integer = 8, fraction = 0, message = "ZIP code must be a number")
    private final String zip;

    // @Digits(integer = 11, fraction = 0, message = "Phone number must be a number")
    // @Size(min = 11, max = 11, message = "Phone number must be 11 digits")
    private final String phone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    
    @Override
    public boolean isAccountNonExpired()    { return true; }
    @Override
    public boolean isAccountNonLocked()     { return true; }
    @Override
    public boolean isCredentialsNonExpired(){ return true; }
    @Override
    public boolean isEnabled()              { return true; }
}
