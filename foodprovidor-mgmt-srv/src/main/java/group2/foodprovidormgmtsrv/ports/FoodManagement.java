package group2.foodprovidormgmtsrv.ports;

import group2.foodprovidormgmtsrv.business.entities.Food;
import group2.foodprovidormgmtsrv.business.entities.FoodProvider;
import group2.foodprovidormgmtsrv.dtos.AddFoodRequest;

import java.util.List;

public interface FoodManagement {
    List<Food> findAll();
    Food findOneById(Long id);
    List<Food> findByName(String name);

    List<Food> foodsByFoodProvider(String name);
    List<Food> foodsByFoodProvider(long id);

    Food add(AddFoodRequest request);

}
