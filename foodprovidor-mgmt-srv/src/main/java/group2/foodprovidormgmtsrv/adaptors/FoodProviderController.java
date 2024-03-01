package group2.foodprovidormgmtsrv.adaptors;


import group2.foodprovidormgmtsrv.business.entities.Food;
import group2.foodprovidormgmtsrv.business.entities.FoodProvider;
import group2.foodprovidormgmtsrv.dtos.AddFoodRequest;
import group2.foodprovidormgmtsrv.dtos.AddProviderRequest;
import group2.foodprovidormgmtsrv.ports.FoodManagement;
import group2.foodprovidormgmtsrv.ports.FoodProviderManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class FoodProviderController {
    private static final String PROVIDER_URL = "/providers";
    private static final String FOOD_URL = "/foods";

    private final FoodManagement foodManagement;
    private final FoodProviderManagement providerManagement;

    @Autowired
    public FoodProviderController(FoodManagement foodManagement, FoodProviderManagement providerManagement) {
        this.foodManagement = foodManagement;
        this.providerManagement = providerManagement;
    }


    @GetMapping(FOOD_URL)
    public List<Food> findAllFoods(){
        return foodManagement.findAll();
    }


    @PostMapping(FOOD_URL +"/add")
    public Food addFood(@RequestBody AddFoodRequest request){
        return foodManagement.add(request);
    }

    @GetMapping(FOOD_URL+"/{id}")
    public Food findById(@PathVariable Long id){
        return foodManagement.findOneById(id);
    }

    @GetMapping("/menu/{name}")
    public List<Food> menu(@PathVariable String name){
        return foodManagement.foodsByFoodProvider(name);
    }

    @GetMapping(FOOD_URL +"/menu/{id}")
    public List<Food> menu(@PathVariable Long id){
        return foodManagement.foodsByFoodProvider(id);
    }

    // +++++++++++++++++


    @GetMapping(PROVIDER_URL)
    public List<FoodProvider> findAllProviders(){
        return providerManagement.findAll();
    }

    @PostMapping(PROVIDER_URL +"/add")
    public FoodProvider addProvider(@RequestBody AddProviderRequest request){
        return providerManagement.add(request);
    }

}
