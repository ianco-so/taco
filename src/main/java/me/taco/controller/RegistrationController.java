package me.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistrationForm (RegistrationForm form) {
        var user = this.tacoUserRepo.save(form.toTacoUser(this.encoder));
        log.info("Form submitted: " + form);
        log.info("User saved: " + user);
        return "redirect:/login";
    }
}
