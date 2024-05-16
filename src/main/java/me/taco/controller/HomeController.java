package me.taco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {
    
    @GetMapping(path="/")
    public ModelAndView getHome() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }
    
}
