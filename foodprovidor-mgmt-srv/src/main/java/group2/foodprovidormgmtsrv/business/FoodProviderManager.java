package group2.foodprovidormgmtsrv.business;

import group2.foodprovidormgmtsrv.business.entities.FoodProvider;
import group2.foodprovidormgmtsrv.dtos.AddProviderRequest;
import group2.foodprovidormgmtsrv.ports.FoodProviderManagement;
import group2.foodprovidormgmtsrv.ports.FoodProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class FoodProviderManager implements FoodProviderManagement {

    private final FoodProviderRepository foodProviderRepository;


    public FoodProviderManager(FoodProviderRepository foodProviderRepository) {
        this.foodProviderRepository = foodProviderRepository;
    }

    @Override
    public List<FoodProvider> findAll() {
        return foodProviderRepository.findAll();
    }

    @Override
    public FoodProvider findOneById(Long id) {
        return foodProviderRepository.findById(id)
                .orElseThrow(() -> new FoodProviderNotFoundException(id));
    }

    @Override
    public FoodProvider findByName(String name) {
        List<FoodProvider> foodProviders = foodProviderRepository.findByName(name);
        if (foodProviders.size() == 0)
            throw new FoodProviderNotFoundException(name);
        return foodProviders.get(0);
    }

    @Override
    public FoodProvider add(AddProviderRequest request) {
        FoodProvider provider = new FoodProvider(request.getName(), request.getTelephone());
        if (exists(provider))
            throw new RuntimeException("Food Provider already exists!");
        FoodProvider saved = foodProviderRepository.save(provider);
        return saved;
    }

    private boolean exists(FoodProvider foodProvider) {
        if (!Objects.isNull(foodProvider.getId())){
            return foodProviderRepository.findById(foodProvider.getId()).isPresent();
        }
        return foodProviderRepository.findByName(foodProvider.getName()).size() > 0;
    }


}
