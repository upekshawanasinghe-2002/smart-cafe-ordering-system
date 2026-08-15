package com.cafe.menu_service.controller;

import com.cafe.menu_service.entity.Food;
import com.cafe.menu_service.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return foodService.addFood(food);
    }

    @GetMapping
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @PutMapping("/{id}")
    public Food updateFood(
            @PathVariable Long id,
            @RequestBody Food food) {

        return foodService.updateFood(id, food);
    }

    @DeleteMapping("/{id}")
    public String deleteFood(@PathVariable Long id) {

        foodService.deleteFood(id);

        return "Food deleted successfully";
    }

    @GetMapping("/search")
    public List<Food> searchFood(@RequestParam String name) {

        return foodService.searchFood(name);
    }
}