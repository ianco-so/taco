package me.taco.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import me.taco.api.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    Page<Taco> findAll(Pageable page);

}
