package me.taco.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.taco.api.model.Taco;
import me.taco.api.repository.TacoRepository;
import me.taco.controller.props.Props;


@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://taco:8080")
public class TacoController {
    
    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private static Props props;

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos(@RequestParam String param) {
        PageRequest page = PageRequest.of(0, TacoController.props.getPageSize(), Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }
}
