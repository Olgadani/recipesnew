package pro.sky.recipesnew.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
public class IngredientServiceimpl implements IngredientService{
    private final Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private long counter = 0;

    private final Path path;
    private final ObjectMapper objectMapper;

    public IngredientServiceimpl(@Value("${application.file.ingredients}") String path) {
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
            Map<Long, Ingredient> mapFromFile = objectMapper.readValue(file, new TypeReference<>() {
            });
            ingredientMap.putAll(mapFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDataToFile(Map<Long, Ingredient> ingredientMap) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(ingredientMap);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Ingredient add(Ingredient ingredient) {
        Ingredient newIngredient = ingredientMap.put(counter++, ingredient);
        writeDataToFile(ingredientMap);
        return newIngredient;
    }
    @Override
    public Ingredient get(long id) {
        return ingredientMap.get(id);
    }
    @Override
    public Ingredient update(long id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            Ingredient newIngredient = ingredientMap.put(id, ingredient);
            writeDataToFile(ingredientMap);
            return newIngredient;
        }
        return null;
    }
    @Override
    public Ingredient remove(long id) {
        Ingredient ingredient = ingredientMap.remove(id);
        writeDataToFile(ingredientMap);
        return ingredient;
    }

    @Override
    public byte[] getAllInBytes() {
        return new byte[0];
    }

    public void importIngredients(MultipartFile ingredients) {
        try {
            Map<Long, Ingredient> mapFromRequest = objectMapper.readValue(ingredients.getBytes(),
                    new TypeReference<>() {
                    });
            writeDataToFile(mapFromRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
