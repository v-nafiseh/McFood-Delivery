package com.example.mcfood.service;

import com.example.mcfood.model.Food;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CartManagement {

    void addFood(Long userId, Long productId);


    void removeFood(Long foodId);

    Map<Food, Integer> getProductsInCart(Long userId);

    List<Food> cartAsaList(Long userId);
    void checkout(Long userId) throws IOException;

    void paymentFinished(Long userId);

    Double calculateTotalPrice(Long userId);

    void emptyCart(Long userId);
}
