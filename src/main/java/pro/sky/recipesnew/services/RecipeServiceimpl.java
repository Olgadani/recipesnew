package pro.sky.recipesnew.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipesnew.model.Ingredient;
import pro.sky.recipesnew.model.Recipe;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
@Service
public class RecipeServiceimpl implements RecipeService {
    private final Map<Long, Recipe> recipeMap = new HashMap<>();
    private long counter = 0L;
    private final Path path;
    private final ObjectMapper objectMapper;

    public RecipeServiceimpl(@Value("application.file.recipes") String path) {
        try {
            this.path = Paths.get(path);
            this.objectMapper = new ObjectMapper();
        } catch (InvalidPathException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @PostConstruct
    public void init() {
        readDataFromFile();
    }

    private void readDataFromFile() {
        try {
            byte[] file = Files.readAllBytes(path);
            Map<Long, Recipe> mapFromFile = objectMapper.readValue(file, new TypeReference<>() {
            });
            recipeMap.putAll(mapFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeDataToFile() {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(recipeMap);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Recipe add(Recipe recipe) {
        Recipe newRecipe = recipeMap.put(counter++, recipe);
        writeDataToFile();
        return newRecipe;
    }
    @Override
    public Recipe get(long id) {
        return recipeMap.get(id);
    }
    @Override
    public Recipe update(long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            Recipe newRecipe = recipeMap.put(id, recipe);
            writeDataToFile();
            return newRecipe;
        }
        return null;
    }
    @Override
    public Recipe remove(long id) {

        Recipe recipe = recipeMap.remove(id);
        writeDataToFile();
        return recipe;
    }

}
