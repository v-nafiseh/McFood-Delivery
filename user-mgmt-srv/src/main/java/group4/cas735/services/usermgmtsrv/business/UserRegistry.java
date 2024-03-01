package group4.cas735.services.usermgmtsrv.business;

import group4.cas735.services.usermgmtsrv.business.entities.Account;
import group4.cas735.services.usermgmtsrv.ports.AccountRepository;
import group4.cas735.services.usermgmtsrv.ports.UserFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserRegistry implements UserFinder {

    private final AccountRepository repository;

    @Autowired
    public UserRegistry(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Account findOneByEmail(String email) {
        List<Account> result = repository.findByEmail(email);
        if (result.size() == 0)
            throw new UserNotFoundException(email);
        return result.get(0);
    }
}
