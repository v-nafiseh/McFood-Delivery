package group2.foodprovidormgmtsrv.ports;

import group2.foodprovidormgmtsrv.business.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByName(String name);


}