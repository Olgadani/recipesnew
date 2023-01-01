package pro.sky.recipesnew.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String appWork() {
        return "Приложение запущено";
    }
    @GetMapping("/info")
    public String page() {
        return "Данилова Ольга"
                + "рецепты"
                + "дата: 01.01.2023"
                + "сайт с рецептами";
    }
}
