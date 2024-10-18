package me.taco.core.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import me.taco.api.model.Ingredient;
import me.taco.api.model.Taco;
import me.taco.api.model.TacoUser;
import me.taco.api.model.Ingredient.Type;
import me.taco.api.repository.IngredientRepository;
import me.taco.api.repository.TacoRepository;
import me.taco.api.repository.TacoUserRepository;

@Profile("!prod")
@Configuration
@Slf4j
public class DevConfig {
    

    @Bean
    public CommandLineRunner dataLoader (
        IngredientRepository ingredientRepo,
        TacoUserRepository userRepo,
        PasswordEncoder encoder,
        TacoRepository tacoRepo
    ) {
        return args -> {
            var flto = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
            var coto = new Ingredient("COTO", "Corn Tortilla",  Type.WRAP);
            var grbf = new Ingredient("GRBF", "Ground Beef",    Type.PROTEIN);
            var carn = new Ingredient("CARN", "Carnitas",       Type.PROTEIN);
            var tmto = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
            var letc = new Ingredient("LETC", "Lettuce",        Type.VEGGIES);
            var ched = new Ingredient("CHED", "Cheddar",        Type.CHEESE);
            var jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
            var slsa = new Ingredient("SLSA", "Salsa",          Type.SAUCE);
            var srcr = new Ingredient("SRCR", "Sour Cream",     Type.SAUCE);

            var ingredients = List.of(
                flto,
                coto,
                grbf,
                carn,
                tmto,
                letc,
                ched,
                jack,
                slsa,
                srcr
            );

            ingredientRepo.saveAll(ingredients);
            //---------------------------------------------------------------------------------------
            
            userRepo.findByUsername("ianco").ifPresentOrElse(
                user -> log.error("User {} already exists", user.getUsername()),
                () -> userRepo.save(new TacoUser(
                    "ianco", 
                    encoder.encode("pass"), 
                    "Ianco Soares Oliveira", 
                    "Avenida João Gomes de Torres, 20", 
                    "Canguaretama", 
                    "RN", 
                    "59190000", 
                    "84981696513"
                ))
            );

            //---------------------------------------------------------------------------------------

            var taco1 = new Taco();
            var taco2 = new Taco();
            var taco3 = new Taco();

            taco1.setTacoName("Carnivore");
            taco1.setIngredients(Arrays.asList(flto, grbf, ched, jack, srcr));
            taco2.setTacoName("Bovine");
            taco2.setIngredients(Arrays.asList(coto, grbf, ched, jack, srcr));
            taco3.setTacoName("Veggie");
            taco3.setIngredients(Arrays.asList(flto, coto, tmto, letc, slsa));

            var tacos = List.of(taco1, taco2, taco3);
            tacoRepo.saveAll(tacos);
        };
    }
}
