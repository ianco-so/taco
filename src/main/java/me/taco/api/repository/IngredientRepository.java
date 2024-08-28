package me.taco.api.repository;

import org.springframework.data.repository.CrudRepository;

import me.taco.api.model.Ingredient;
/**
 * <h4>
 *  This interface is used to interact with the database. And it provides the basic CRUD operations.
 * </h4>
 * <p>
 *  We could use  interface instead of {@link CrudRepository} interface, but CrudRepository
 *  provides more functionality than {@link org.springframework.data.repository.Repository Repository} interface.
 * </p>
 * 
 * @author <a href="https://www.github.com/ianco-so">ianco</a>
 * @version 0.0.3
 * @since 0.0.3
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}