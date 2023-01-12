package pro.sky.recipesnew.services;

import pro.sky.recipesnew.model.Ingredient;

public interface IngredientService {


    Ingredient add(Ingredient ingredient);

    Ingredient get(long id);

    Ingredient update(long id, Ingredient ingredient);

    Ingredient remove(long id);
}
