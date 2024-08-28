package me.taco.api.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import me.taco.api.model.TacoOrder;
import me.taco.api.model.TacoUser;

/**
 * <h4>
 *  This interface is used to interact with the database. And it provides the basic CRUD operations.
 * </h4>
 * <p>
 *  We could use  interface instead of {@link CrudRepository} interface, but CrudRepository
 *  provides more functionality than {@link org.springframework.data.repository.Repository Repository} interface.
 * </p>
 * 
 * @version 0.0.3
 * @since 0.0.3
 * @author <a href="https://www.github.com/ianco-so">ianco</a>
 */
public interface OrderRepository extends CrudRepository<TacoOrder, Long>{

    List<TacoOrder> findAllByUserOrderByPlacedAtDesc(TacoUser user, Pageable pageable);

}
