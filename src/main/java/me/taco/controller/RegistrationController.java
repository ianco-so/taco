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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.taco.api.model.dto.RegistrationForm;
import me.taco.api.repository.TacoUserRepository;



@Controller
@RequestMapping(path = "/register")
// @SessionAttributes("registrationForm")
@Slf4j
public class RegistrationController {
    
    @Autowired
    private TacoUserRepository tacoUserRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public String registerForm(Model model) {
        // if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new RegistrationForm());
        // }
        return "registration";
    }

    @PostMapping
    public String processRegistrationForm (
        @Valid RegistrationForm form,
        BindingResult result,
        RedirectAttributes attr
    ) {
        if (result.hasErrors()) {
            log.error("Errors: {}", result.getAllErrors());
            return "registration";
        }
        var user = form.toTacoUser(this.encoder);
        try {
            user = this.tacoUserRepo.save(user);
        } catch (DataIntegrityViolationException dive) {
            result.rejectValue("username", "error.username", "Username already exists");
            return "registration";
        } catch (Exception e) {
            log.error("Error saving user: " + user, e);
            result.rejectValue("username", "error.username", "Error saving user");
            return "registration";
        }
        log.info("Form submitted: " + form);
        log.info("User saved: " + user);
        return "redirect:/login";
    }
}
