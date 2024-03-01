package group2.bookstoremgmtsrv.ports;

import group2.bookstoremgmtsrv.business.entities.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {

    List<Good> findByTitle(String title);


}