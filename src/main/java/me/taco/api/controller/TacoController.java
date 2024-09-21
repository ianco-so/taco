package me.taco.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.taco.api.controller.props.Props;
import me.taco.api.model.Taco;
import me.taco.api.repository.TacoRepository;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://taco:8080")
public class TacoController {
    
    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private Props props;
    
    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, this.props.getPageSize(), Sort.by("createdAt").descending());
        return tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public Optional<Taco> tacoById (@PathVariable("id") Long id) {
        return this.tacoRepository.findById(id);
    }
}
