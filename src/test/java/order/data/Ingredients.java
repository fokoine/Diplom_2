package order.data;


import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    private List<String> ingredients;

    public Ingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return (ArrayList<String>) ingredients;
    }

    public void setIngredients(int index, String ingredientId) {
        ingredients.add(index, ingredientId);
    }
}
