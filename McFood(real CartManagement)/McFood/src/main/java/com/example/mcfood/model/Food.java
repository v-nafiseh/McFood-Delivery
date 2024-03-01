package com.example.mcfood.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Food {
    private @Id
    @GeneratedValue Long id;

    private @NonNull Long userId;
    private @NonNull String name;
    private @NonNull String description;
    private @NonNull double price;

    private @NonNull String foodProvider;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return name.equals(food.name) && foodProvider.equals(food.foodProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, price, foodProvider);
    }
}