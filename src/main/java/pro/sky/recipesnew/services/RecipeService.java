package pro.sky.recipesnew.services;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesnew.model.Recipe;

import java.io.IOException;

public interface RecipeService {
    Recipe add(Recipe recipe);

    Recipe get(long id);

    Recipe update(long id, Recipe recipe);

    Recipe remove(long id);
    byte[] getAllInBytes();

    void importRecipes(MultipartFile recipes) throws IOException;

    byte[] exportTxt() throws IOException;
}
