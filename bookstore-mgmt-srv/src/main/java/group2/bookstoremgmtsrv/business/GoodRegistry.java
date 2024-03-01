package group2.bookstoremgmtsrv.business;

import group2.bookstoremgmtsrv.business.entities.Good;
import group2.bookstoremgmtsrv.ports.GoodFinder;
import group2.bookstoremgmtsrv.ports.GoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class GoodRegistry implements GoodFinder {

    private final GoodRepository repository;

    public GoodRegistry(GoodRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Good> findAll() {
        return repository.findAll();
    }

    @Override
    public Good findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));
    }

    @Override
    public Good findOneByTitle(String title) {
        List<Good> result = repository.findByTitle(title);
        if (result.size() == 0)
            throw new GoodNotFoundException(title);
        return result.get(0);
    }

    @Override
    public List<Good> catalogue(double mdd) {
        List<Good> allGoods = repository.findAll();
        ArrayList<Good> result = new ArrayList<>();
        for (Good good : allGoods){
            if (good.getPrice() <= mdd){
                result.add(good);
            }
        }
        return result;
    }
}
