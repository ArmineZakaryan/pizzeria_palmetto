package am.trainings.palmetto.customer;

import am.trainings.palmetto.pizza.Pizza;
import am.trainings.palmetto.pizza.PizzaKeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Order {

    private Long orderId;
    private Long customerId;

    @JsonDeserialize(keyUsing = PizzaKeyDeserializer.class)
    private Map<Pizza, Integer> pizzas = new HashMap<>();

    public Order(Long customerId, Map<Pizza, Integer> pizzas) {
        this.customerId = customerId;
        this.pizzas = new HashMap<>(pizzas);
    }

    public void addPizza(Pizza pizza, int count) {
        pizzas.put(pizza, count);
    }
}