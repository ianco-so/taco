package me.taco.api.repository;

import org.springframework.data.repository.CrudRepository;

import me.taco.api.model.TacoUser;

public interface TacoUserRepository extends CrudRepository<TacoUser, Long> {
    TacoUser findByUsername (String username);
}
