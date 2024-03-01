package group2.foodprovidormgmtsrv.ports;

import group2.foodprovidormgmtsrv.business.entities.Food;
import group2.foodprovidormgmtsrv.business.entities.FoodProvider;
import group2.foodprovidormgmtsrv.dtos.AddProviderRequest;

import java.util.List;

public interface FoodProviderManagement {
    List<FoodProvider> findAll();
    FoodProvider findOneById(Long id);
    FoodProvider findByName(String name);

    FoodProvider add(AddProviderRequest request);





}
