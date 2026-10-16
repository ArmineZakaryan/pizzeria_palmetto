package am.trainings;

import java.util.Arrays;

/**
        * YAGNI, KISS, DRY
 */
public class Pizza {

    public static final Pizza REGULAR = new Pizza("Regular", PizzaType.REGULAR.name(), new String[7]);

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

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void addIngredient(String ingredient) {
        if (ingredientsCount < ingredients.length) {
            ingredients[ingredientsCount++] = ingredient;
        }
    }

    public int getIngredientsCount() {
        return ingredientsCount;
    }

    @Override
    public String toString() {
        String joined = Arrays.stream(ingredients)
                .filter(i -> i != null)
                .reduce((a, b) -> a + ", " + b)
                .orElse("none");
        return "Pizza{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", ingredients=" + joined +
                ", ingredientsCount=" + ingredientsCount +
                '}';
    }
}