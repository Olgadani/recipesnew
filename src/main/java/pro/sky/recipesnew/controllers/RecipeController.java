package pro.sky.recipesnew.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipesnew.model.Recipe;
import pro.sky.recipesnew.services.RecipeService;
@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")@Operation(description = "Получение нового рецепта")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Получение выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Рецепт не найден", content = {}
            )
    })
    public Recipe getRecipe(@PathVariable("id") long id) {
        return recipeService.get(id);
    }


    @PutMapping("/{id}")
    @Operation(description = "Обновление рецепта")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Обновление выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Рецепт не найден", content = {}
            )
    })
    public Recipe updateRecipe(@PathVariable("id") long id, @RequestBody Recipe recipe) {
        return recipeService.update(id, recipe);
    }

    @PostMapping
    @Operation(description = "Добавление рецепта")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Добавление выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Рецепт не найден", content = {}
            )
    })
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        if (StringUtils.isEmpty(recipe.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(recipeService.add(recipe));
    }
    @DeleteMapping("/{id}")
    @Operation(description = "Удаление рецепта")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Удаление выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Рецепт   не найден", content = {}
            )
    })
    public Recipe deleteRecipe(@PathVariable("id") long id) {
        return recipeService.remove(id);
    }

}
