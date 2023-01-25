package pro.sky.recipesnew.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipesnew.model.Ingredient;
import pro.sky.recipesnew.services.IngredientService;

import java.io.IOException;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение нового ингредиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Получение выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Ингредиент не найден", content = {}
            )
    })
    public Ingredient getIngredient(@PathVariable("id") long id) {
        return this.ingredientService.get(id);
    }
    @PostMapping
    @Operation(description = "Добавление ингредиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Добавление выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Ингредиент не найден", content = {}
            )
    })
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.add(ingredient);
    }
    @PutMapping("/{id}")
    @Operation(description = "Обновление ингредиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Обновление выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Ингредиент не найден", content = {}
            )
    })
    public Ingredient updateIngredient(@PathVariable("id") long id, @RequestBody Ingredient ingredient) {
        return ingredientService.update(id, ingredient);
    }
    @DeleteMapping("/{id}")
    @Operation(description = "Удаление ингредиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Удаление выполнено успешно",
                    content = {@Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(responseCode = "404",
                    description = "Ингредиент не найден", content = {}
            )
    })
    public Ingredient deleteIngredient(@PathVariable("id") long id) {
        return ingredientService.remove(id);
    }
    @GetMapping("/exportRecipes")
    public ResponseEntity<byte[]> downloadIngredients() throws IOException {
        byte[] bytes = ingredientService.getAllInBytes();
        if (bytes == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename = \"ingredients.json\"")
                .body(bytes);
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importIngredients(MultipartFile ingredients) throws IOException {
        ingredientService.importIngredients(ingredients);
    }


}
