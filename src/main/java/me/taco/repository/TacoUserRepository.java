package me.taco.repository;

import org.springframework.data.repository.CrudRepository;

import me.taco.model.TacoUser;

public interface TacoUserRepository extends CrudRepository<TacoUser, Long> {
    TacoUser findByUsername (String username);
}
