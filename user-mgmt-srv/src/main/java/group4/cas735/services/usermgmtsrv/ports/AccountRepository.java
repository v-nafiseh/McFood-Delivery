package group4.cas735.services.usermgmtsrv.ports;

import group4.cas735.services.usermgmtsrv.business.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByEmail(String email);

}
