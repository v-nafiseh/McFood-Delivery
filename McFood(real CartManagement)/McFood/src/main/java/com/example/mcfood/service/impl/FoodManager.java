package com.example.mcfood.service.impl;

import com.example.mcfood.model.Food;
import com.example.mcfood.repository.FoodRepository;
import com.example.mcfood.service.FoodManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class FoodManager implements FoodManagement {

    private final FoodRepository repository;

    public FoodManager(FoodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Food> findAll() {
        return repository.findAll();
    }

    @Override
    public Food findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Food> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Map<Food, Integer> getCart(Long userId) {
        List<Food> foods = findAll();
        Map<Food, Integer> cart = new HashMap<>();
        for (Food f: foods){
            if (f.getUserId() == userId){
                if (cart.containsKey(f)) {
                    cart.replace(f, cart.get(f) + 1);
                } else {
                    cart.put(f, 1);
                }
            }
        }
        return cart;
    }
}
