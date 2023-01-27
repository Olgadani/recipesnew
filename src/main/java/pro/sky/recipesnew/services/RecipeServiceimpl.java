package pro.sky.recipesnew.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesnew.model.Ingredient;
import pro.sky.recipesnew.model.Recipe;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
    private final Path pathToTxtTemplate;
    private final ObjectMapper objectMapper;

    public RecipeServiceimpl(@Value("${application.file.recipes}") String path) {
        try {
            this.path = Paths.get(path);
            this.pathToTxtTemplate = Paths.get(RecipeServiceimpl.class.getResource("recipesTemplate.txt").toURI());
            this.objectMapper = new ObjectMapper();
        } catch (InvalidPathException e) {
            e.printStackTrace();
            throw e;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
    private void writeDataToFile(Map<Long, Recipe> recipeMap) {
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
        writeDataToFile(recipeMap);
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
            writeDataToFile(recipeMap);
            return newRecipe;
        }
        return null;
    }
    @Override
    public Recipe remove(long id) {

        Recipe recipe = recipeMap.remove(id);
        writeDataToFile(recipeMap);
        return recipe;
    }

        @Override
    public byte[] getAllInBytes() {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void importRecipes(MultipartFile recipes) {
        try {
            Map<Long, Recipe> mapFromRequest = objectMapper.readValue(recipes.getBytes(),
                    new TypeReference<>() {
            });
            writeDataToFile(mapFromRequest);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    @Override
    public byte[] exportTxt() {
        try {
            String template = Files.readString(pathToTxtTemplate, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            for (Recipe recipe : recipeMap.values()) {
                StringBuilder ingredients = new StringBuilder();
                StringBuilder steps = new StringBuilder();
                for (Ingredient ingredient : recipe.getIngredients()) {
                    ingredients.append(" - ").append(ingredient).append("\n");
                }
                int stepCounter = 1;
                for (String step : recipe.getSteps()) {
                    steps.append(stepCounter++).append(". ").append(step).append("\n");
                }
                String recipeData = template.replace("%title%", recipe.getTitle())
                        .replace("%cookingTime%", String.valueOf(recipe.getCookingTime()))
                        .replace("%ingredients%", ingredients.toString())
                        .replace("%steps%", steps.toString());
                stringBuilder.append(recipeData).append("\n\n\n");
            }
            return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
