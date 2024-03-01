package com.cas735.group2.Trackersrv.ports;

import com.cas735.group2.Trackersrv.business.entities.Food;
import com.cas735.group2.Trackersrv.dtos.FoodRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FoodManagement {
    List<Food> findAll();
    Food findOneById(Long id);
    List<Food> findByName(String name);

    List<Food> findByUserId(Long userId);
    List<Food> findByProviderName(String provider);

    void addFood(List<FoodRequest> request);

    boolean foodGotReady(FoodRequest request);

    boolean foodPickedUp(FoodRequest request);

}
