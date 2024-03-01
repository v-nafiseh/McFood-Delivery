package com.example.mcfood.controller;

import com.example.mcfood.model.Food;
import com.example.mcfood.service.CartManagement;
import com.example.mcfood.service.FoodManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class CartController {

    private final FoodManagement foodManagement;
    private final CartManagement cartManagement;

    @Autowired
    public CartController(FoodManagement foodManagement, CartManagement cartManagement) {
        this.foodManagement = foodManagement;
        this.cartManagement = cartManagement;
    }

    @GetMapping("/shoppingCart/{userId}")
    public Map<Food, Integer> shoppingCart(@PathVariable Long userId) {
        return cartManagement.getProductsInCart(userId);
    }

    @GetMapping("/shoppingCart/empty/{userId}")
    public void emptyCart(@PathVariable Long userId){
        cartManagement.emptyCart(userId);
    }

    @GetMapping("/shoppingCart/totalMoney/{userId}")
    public Double totalMoney(@PathVariable Long userId){
        return cartManagement.calculateTotalPrice(userId);
    }

    @GetMapping("/shoppingCart/addToCart")
    public void addProductToCart(@RequestParam Long userId, Long foodId) {
        cartManagement.addFood(userId, foodId);
    }


    @GetMapping("/shoppingCart/removeFromCart")
    public void removeProductFromCart(@RequestParam Long foodId) {
        cartManagement.removeFood(foodId);
    }

    @GetMapping("/shoppingCart/checkout/{userId}")
    public void checkout(@PathVariable Long userId) {
        try {
            cartManagement.checkout(userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/shoppingCart/paymentDone/{userId}")
    public void paymentDone(@PathVariable Long userId) {
        cartManagement.paymentFinished(userId);
    }




}
