package group2.foodprovidormgmtsrv.business;


import group2.foodprovidormgmtsrv.business.entities.Food;
import group2.foodprovidormgmtsrv.business.entities.FoodProvider;
import group2.foodprovidormgmtsrv.dtos.AddFoodRequest;
import group2.foodprovidormgmtsrv.ports.FoodManagement;
import group2.foodprovidormgmtsrv.ports.FoodProviderRepository;
import group2.foodprovidormgmtsrv.ports.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FoodManager implements FoodManagement {

    private final FoodRepository foodRepository;
    private final FoodProviderRepository foodProviderRepository;

    public FoodManager(FoodRepository foodRepository, FoodProviderRepository foodProviderRepository) {
        this.foodRepository = foodRepository;
        this.foodProviderRepository = foodProviderRepository;
    }


    @Override
    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Food findOneById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
    }

    @Override
    public List<Food> findByName(String name) {
        List<Food> foods = foodRepository.findByName(name);
        if (foods.size() == 0)
            throw new FoodNotFoundException(name);
        return foods;
    }

    @Override
    public List<Food> foodsByFoodProvider(String name) {
        List<Food> allFoods = foodRepository.findAll();
        ArrayList<Food> foods = new ArrayList<>();
        for (Food f :
                allFoods) {
            if (f.getFoodProvider().getName().equals(name)) {
                foods.add(f);
            }
        }
        return foods;
    }

    @Override
    public List<Food> foodsByFoodProvider(long id) {
        List<Food> allFoods = foodRepository.findAll();
        ArrayList<Food> foods = new ArrayList<>();
        for (Food f :
                allFoods) {
            if (f.getFoodProvider().getId() == id) {
                foods.add(f);
            }
        }
        return foods;
    }

    @Override
    public Food add(AddFoodRequest request) {
        Food food = new Food(request.getName(), request.getDescription(),
                request.getPrice(), foodProviderRepository.findByName(request.getProviderName()).get(0));
        if (exists(food))
            throw new RuntimeException("This food already exists!");
        Food saved = foodRepository.save(food);
        return saved;
    }

    private boolean exists(Food food) {
        List<Food> foods = foodRepository.findByName(food.getName());
        for (Food f :
                foods) {
            if (f.getFoodProvider().equals(food.getFoodProvider()))
                return true;

        }
        return false;
    }
}
