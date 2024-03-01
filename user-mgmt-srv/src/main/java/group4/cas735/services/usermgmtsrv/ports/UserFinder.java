package group4.cas735.services.usermgmtsrv.ports;


import group4.cas735.services.usermgmtsrv.business.entities.Account;

import java.util.List;

public interface UserFinder {

    List<Account> findAll();
    Account findOneById(Long id);
    Account findOneByEmail(String email);

}
