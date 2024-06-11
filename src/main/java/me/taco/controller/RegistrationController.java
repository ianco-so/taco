package me.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.taco.model.dto.RegistrationForm;
import me.taco.repository.TacoUserRepository;



@Controller
@RequestMapping(path = "/register")
public class RegistrationController {
    
    @Autowired
    private TacoUserRepository tacoUserRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistrationForm (RegistrationForm form) {
        this.tacoUserRepo.save(form.toTacoUser(this.encoder));
        return "redirect:/login";
    }
}
