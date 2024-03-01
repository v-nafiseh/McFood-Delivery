package com.cas735.group2.Trackersrv.adaptors;


import com.cas735.group2.Trackersrv.business.entities.Food;
import com.cas735.group2.Trackersrv.dtos.FoodRequest;
import com.cas735.group2.Trackersrv.ports.FoodManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class TrackerController {
    private static final String ENDPOINT = "/tracker";
    private final FoodManagement manager;

    @Autowired
    public TrackerController(FoodManagement manager) {
        this.manager = manager;
    }


    @GetMapping(ENDPOINT)
    public List<Food> findAll(){
        return manager.findAll();
    }

    @GetMapping(ENDPOINT+"/provider/{providerName}")
    public List<Food> showProviderPendingList(@PathVariable String providerName){
        return manager.findByProviderName(providerName);
    }


    @GetMapping(ENDPOINT+"/user/{userId}")
    public List<Food> showUserPendingList(@PathVariable Long userId){
        return manager.findByUserId(userId);
    }

    @PostMapping(ENDPOINT+"/add")
    public void addFoods(@RequestBody List<FoodRequest> requests){
        manager.addFood(requests);
    }


    @PostMapping(ENDPOINT+"/ready")
    public boolean foodGotReady(@RequestBody FoodRequest request){
        return manager.foodGotReady(request);
    }

    @PostMapping(ENDPOINT+"/pickup")
    public boolean foodPickedUp(@RequestBody FoodRequest request){
        return manager.foodPickedUp(request);
    }



}
