package me.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.taco.api.model.dto.RegistrationForm;
import me.taco.api.repository.TacoUserRepository;



@Controller
@RequestMapping(path = "/register")
@Slf4j
public class RegistrationController {
    
    @Autowired
    private TacoUserRepository tacoUserRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "Registration";
    }

    @PostMapping
    public String processRegistrationForm (
        @Valid RegistrationForm form,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "Registration";
        }
        var user = form.toTacoUser(this.encoder);
        try {
            user = this.tacoUserRepo.save(user);
            log.info("Saving user {}", user);
        } catch (DataIntegrityViolationException dive) {
            result.rejectValue("username", "error.username", "Username already exists");
            return "Registration";
        }
        return "redirect:/login";
    }
}
