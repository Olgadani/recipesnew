package pro.sky.recipesnew.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipesnew.model.Recipe;
import pro.sky.recipesnew.services.RecipeService;
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable("id") long id) {
        return recipeService.get(id);
    }


    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable("id") long id, @RequestBody Recipe recipe) {
        return recipeService.update(id, recipe);
    }

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        if (StringUtils.isEmpty(recipe.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recipeService.add(recipe));
    }
    @DeleteMapping("/{id}")
    public Recipe deleteRecipe(@PathVariable("id") long id) {
        return recipeService.remove(id);
    }

}
