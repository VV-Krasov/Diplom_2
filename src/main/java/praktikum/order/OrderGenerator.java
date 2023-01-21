package praktikum.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderGenerator {
    public static Order createRandomOrder(List<String> availableIngredients)
    {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(availableIngredients.get(
                new Random().nextInt(availableIngredients.size())));

        return new Order(ingredients);
    }

    public static Order createRandomOrderWithWrongHashIngredients()
    {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(String.valueOf(new Random().nextInt(999999999)));

        return new Order(ingredients);
    }
}
