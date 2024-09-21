package me.taco.core.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.taco.api.model.Ingredient;
import me.taco.api.model.Taco;
import me.taco.api.model.TacoUser;
import me.taco.api.model.Ingredient.Type;
import me.taco.api.repository.IngredientRepository;
import me.taco.api.repository.TacoRepository;
import me.taco.api.repository.TacoUserRepository;

@Profile("!prod")
@Configuration
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

            ingredientRepo.save(flto);
            ingredientRepo.save(coto);
            ingredientRepo.save(grbf);
            ingredientRepo.save(carn);
            ingredientRepo.save(tmto);
            ingredientRepo.save(letc);
            ingredientRepo.save(ched);
            ingredientRepo.save(jack);
            ingredientRepo.save(slsa);
            ingredientRepo.save(srcr);

            //---------------------------------------------------------------------------------------

            userRepo.save(new TacoUser(
                "ianco",
                encoder.encode("ianco"),
                "Ianco Soares Oliveira",
                "Avenida João Gomes de Torres, 20",
                "Canguaretama",
                "RN",
                "59190000",
                "84981696513"
            ));

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

            tacoRepo.save(taco1);
            tacoRepo.save(taco2);
            tacoRepo.save(taco3);
        };
    }
}
