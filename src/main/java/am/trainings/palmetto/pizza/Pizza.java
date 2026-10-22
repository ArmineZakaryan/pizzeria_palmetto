package am.trainings.palmetto.pizza;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "type")
public class Pizza {

    public static final Pizza REGULAR = new Pizza("Regular", PizzaType.REGULAR.name(), new String[7]);
    public static final Pizza CALZONE = new Pizza("Calzone", PizzaType.CALZONE.name(), new String[7]);

    private String name;
    private String type;
    private String[] ingredients;
    private int ingredientsCount;

    public Pizza(String name, String type, String[] ingredients) {
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
        for (String ingredient : ingredients) {
            if (ingredient != null) {
                ingredientsCount++;
            }
        }
    }

    public static Pizza fromType(String type) {
        if (PizzaType.REGULAR.name().equals(type)) {
            return REGULAR;
        }
        if (PizzaType.CALZONE.name().equals(type)) {
            return CALZONE;
        }
        throw new IllegalArgumentException("Unknown pizza type: " + type);
    }

    @Override
    public String toString() {
        return type;
    }
}