package pro.sky.recipesnew.services;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesnew.model.Ingredient;
import pro.sky.recipesnew.model.Recipe;

import java.io.IOException;
import java.util.Map;

public interface IngredientService {


    Ingredient add(Ingredient ingredient);

    Ingredient get(long id);

    Ingredient update(long id, Ingredient ingredient);

    Ingredient remove(long id);

    byte[] getAllInBytes();


    void importIngredients(MultipartFile ingredients);
}
