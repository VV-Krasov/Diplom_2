package praktikum.order;

import java.util.List;

public class Order {
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients() {
    }

    private List<String> ingredients;

    public Order(List<String> ingredients)
    {
        this.ingredients = ingredients;
    }
}
