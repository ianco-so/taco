package me.taco.api.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.taco.api.model.enums.StateCode;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class TacoUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    public static final int MIN_STR_LEN = 5;
    public static final int MAX_STR_LEN = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, updatable = false)
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Username must be between " +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private final String username;

    @NonNull
    @NotBlank
    @Column(nullable = false)
    private String password;

    @NonNull
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Fullname must be between "   +MIN_STR_LEN+" and "+MAX_STR_LEN+"characters long")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Invalid full name, use only alphabetic characters and spaces")
    private String fullname;

    @NonNull
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "Street must be between "     +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    private String street;

    @NonNull
    @Size(min = MIN_STR_LEN, max = MAX_STR_LEN, message = "City must be between "       +MIN_STR_LEN+" and "+MAX_STR_LEN+" characters long")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Invalid city, use only alphabetic characters and spaces")
    private String city;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StateCode state;

    @NonNull
    @Digits(integer = 8, fraction = 0, message = "ZIP code must be 8 digits")
    private String zip;

    @NonNull
    @Digits(integer = 11, fraction = 0, message = "Phone number must be 11 digits")
    private String phone;

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
