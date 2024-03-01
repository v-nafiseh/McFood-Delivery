package com.cas735.group2.Trackersrv.ports;

import com.cas735.group2.Trackersrv.business.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByName(String name);


}
