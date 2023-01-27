package pro.sky.recipesnew.model;

import java.util.List;

public class Recipe {
    private String title;
    private int cookingTime;
    private List<Ingredient>ingredients;
    private List<String> steps;

    public String getTitle() {
        return title;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }
}
