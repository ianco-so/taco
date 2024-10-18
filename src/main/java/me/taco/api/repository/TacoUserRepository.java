package me.taco.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import me.taco.api.model.TacoUser;

public interface TacoUserRepository extends CrudRepository<TacoUser, Long> {
    Optional<TacoUser> findByUsername (String username);
}
