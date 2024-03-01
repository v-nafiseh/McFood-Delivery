package group2.foodprovidormgmtsrv.ports;

import group2.foodprovidormgmtsrv.business.entities.FoodProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodProviderRepository extends JpaRepository<FoodProvider, Long> {

    List<FoodProvider> findByName(String name);


}