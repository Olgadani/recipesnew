package pro.sky.recipesnew.services;

import org.springframework.stereotype.Service;
import pro.sky.recipesnew.model.Recipe;

import java.util.HashMap;
import java.util.Map;
@Service
public class RecipeServiceimpl implements RecipeService {
    private final Map<Long, Recipe> recipeMap = new HashMap<>();
    private long counter = 0L;
    @Override
    public Recipe add(Recipe recipe) {
        recipeMap.put(this.counter++, recipe);
        return recipe;
    }
    @Override
    public Recipe get(long id) {
        return recipeMap.get(id);
    }
    @Override
    public Recipe update(long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            return recipe;
        }
        return null;
    }
    @Override
    public Recipe remove(long id) {
        return recipeMap.remove(id);
    }

}
