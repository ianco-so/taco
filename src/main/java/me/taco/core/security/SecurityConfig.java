package me.taco.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import me.taco.repository.TacoUserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    //     List<UserDetails> users = new ArrayList<>();
    //     users.add(new User(
    //         "buzz", 
    //         encoder.encode("pass"), 
    //         Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
    //     ));
    //     users.add(new User(
    //         "woody",
    //         encoder.encode("pass"),
    //         Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
    //     ));
    //     return new InMemoryUserDetailsManager(users);
    // }
    
    @Bean
    public UserDetailsService userDetailsService(TacoUserRepository tacoUserRepo) {
        return username -> {
            var tacoUser = tacoUserRepo.findByUsername(username);
            if (tacoUser != null) {
                return tacoUser;
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/design", "/orders").hasRole("USER")
            .requestMatchers("/", "/**").permitAll()
        ).formLogin(login -> login
            .loginPage("/login")
            .defaultSuccessUrl("/design")
        ).logout(Customizer.withDefaults());

        return http.build();
    }
}
