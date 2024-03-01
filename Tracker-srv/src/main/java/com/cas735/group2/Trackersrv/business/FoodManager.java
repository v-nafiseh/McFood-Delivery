package com.cas735.group2.Trackersrv.business;

import com.cas735.group2.Trackersrv.business.entities.Food;
import com.cas735.group2.Trackersrv.business.entities.Status;
import com.cas735.group2.Trackersrv.dtos.FoodRequest;
import com.cas735.group2.Trackersrv.ports.FoodManagement;
import com.cas735.group2.Trackersrv.ports.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
                .orElseThrow(() -> new RuntimeException("food with id "+id+" was not found!"));
    }

    @Override
    public List<Food> findByName(String name) {
        List<Food> foods = repository.findByName(name);
        if (foods.size() == 0)
            throw new RuntimeException("food with name "+name+" was not found!");
        return foods;
    }

    @Override
    public List<Food> findByUserId(Long userId) {
        List<Food> foods = findAll();
        ArrayList<Food> ans = new ArrayList<>();
        for (Food f : foods){
            if (f.getUserId() == userId)
                ans.add(f);
        }
        return ans;
    }

    @Override
    public List<Food> findByProviderName(String provider) {
        List<Food> foods = findAll();
        ArrayList<Food> ans = new ArrayList<>();
        for (Food f : foods){
            if (f.getFoodProvider().equals(provider))
                ans.add(f);
        }
        return ans;
    }

    @Override
    public void addFood(List<FoodRequest> request) {
        for (FoodRequest req : request) {
            Food f = new Food(req.getUserId(), req.getName(), req.getProviderName(), Status.PENDING);
            repository.save(f);
        }
    }

    @Override
    public boolean foodGotReady(FoodRequest request) {
        List<Food> foods = findByUserId(request.getUserId());

        for (Food f : foods){
            if (f.getFoodProvider().equals(request.getProviderName()) && f.getName().equals(request.getName())){
                f.setStatus(Status.READY);
                repository.save(f);
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean foodPickedUp(FoodRequest request) {
        List<Food> foods = findByUserId(request.getUserId());

        for (Food f : foods){
            if (f.getFoodProvider().equals(request.getProviderName()) && f.getName().equals(request.getName())){
                repository.delete(f);
                return true;
            }
        }
        return false;
    }
}
