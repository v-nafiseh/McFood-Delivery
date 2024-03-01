package group2.bookstoremgmtsrv.ports;

import group2.bookstoremgmtsrv.business.entities.Good;

import java.util.List;

public interface GoodFinder {
    List<Good> findAll();
    Good findOneById(Long id);
    Good findOneByTitle(String title);

    List<Good> catalogue(double mdd);
}
