package com.example.mcfood.exception;

import com.example.mcfood.model.Food;

public class NotEnoughProductsInStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public NotEnoughProductsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductsInStockException(Food food) {
        super(String.format("Not enough %s products in stock. Only %d left", food.getName()));
    }
}