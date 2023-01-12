package pro.sky.recipesnew.services;

import pro.sky.recipesnew.model.Recipe;

public interface RecipeService {
    Recipe add(Recipe recipe);

    Recipe get(long id);

    Recipe update(long id, Recipe recipe);

    Recipe remove(long id);
}
