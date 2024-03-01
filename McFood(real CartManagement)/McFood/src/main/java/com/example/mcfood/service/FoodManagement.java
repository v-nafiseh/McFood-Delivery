package com.example.mcfood.service;

import com.example.mcfood.model.Food;

import java.util.List;
import java.util.Map;

public interface FoodManagement {

    List<Food> findAll();
    Food findOneById(Long id);
    List<Food> findByName(String name);

    Map<Food, Integer> getCart(Long userId);

}
