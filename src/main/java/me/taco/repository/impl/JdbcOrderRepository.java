package me.taco.repository.impl;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import me.taco.model.IngredientRef;
import me.taco.model.Taco;
import me.taco.model.TacoOrder;
import me.taco.repository.OrderRepository;

@Repository
@AllArgsConstructor
public class JdbcOrderRepository implements OrderRepository{
    
    private JdbcOperations jdbcOps;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf =
        new PreparedStatementCreatorFactory(
            "INSERT INTO Taco_Order "
            + "(client_name, client_street, client_city, "
            + "client_state, zip, cc_number, "
            + "cc_expiration, cc_cvv, placed_at) "
            + "VALUES (?,?,?,?,?,?,?,?,?)",
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        PreparedStatementCreator psc =
            pscf.newPreparedStatementCreator(Arrays.asList(
                order.getClientName(),
                order.getClientStreet(),
                order.getClientCity(),
                order.getClientState(),
                order.getZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCvv(),
                order.getPlacedAt()
            ));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOps.update(psc, keyHolder);
        @SuppressWarnings("null")
        Long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i=0;
        for (Taco taco : tacos) {
            this.saveTaco(orderId, i++, taco);
        }

        return order;
    }
    
    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
            "INSERT INTO Taco "
            + "(taco_name, created_at, taco_order, taco_order_key) "
            + "VALUES (?, ?, ?, ?)",
            Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
        );
        pscf.setReturnGeneratedKeys(true);
    
        PreparedStatementCreator psc =
            pscf.newPreparedStatementCreator(Arrays.asList(
                taco.getTacoName(),
                taco.getCreatedAt(),
                orderId,
                orderKey
            ));
    
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOps.update(psc, keyHolder);
        @SuppressWarnings("null")
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
    
        this.saveIngredientRefs(tacoId, taco.getIngredients());
    
        return tacoId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcOps.update(
                "INSERT INTO Ingredient_Ref (ingredient, taco, taco_key) "
                + "VALUES (?, ?, ?)",
                ingredientRef.getIngredient(), 
                tacoId, 
                key++
            );
        }
    }
    
}
