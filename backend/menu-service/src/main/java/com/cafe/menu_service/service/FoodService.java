package com.cafe.menu_service.service;

import com.cafe.menu_service.entity.Food;
import com.cafe.menu_service.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    public Food updateFood(Long id, Food food) {

        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        existingFood.setName(food.getName());
        existingFood.setPrice(food.getPrice());
        existingFood.setCategory(food.getCategory());
        existingFood.setDescription(food.getDescription());
        existingFood.setAvailability(food.isAvailability());

        return foodRepository.save(existingFood);
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
    }

    public List<Food> searchFood(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }
}