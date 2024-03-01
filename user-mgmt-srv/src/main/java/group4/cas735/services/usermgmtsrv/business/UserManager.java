package group4.cas735.services.usermgmtsrv.business;

import group4.cas735.services.usermgmtsrv.business.entities.Account;
import group4.cas735.services.usermgmtsrv.dtos.RegistrationRequest;
import group4.cas735.services.usermgmtsrv.dtos.UpdateMDDRequest;
import group4.cas735.services.usermgmtsrv.ports.AccountRepository;
import group4.cas735.services.usermgmtsrv.ports.UserFinder;
import group4.cas735.services.usermgmtsrv.ports.UserManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserManager implements UserManagement {

    private final AccountRepository repository;
    private final UserFinder finder;

    @Autowired
    public UserManager(AccountRepository repository, UserFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }



    @Override
    public Account create(RegistrationRequest reg) {
        Account anAccount = new Account(reg.getName(), reg.getEmail());
        if(exists(anAccount)) {
            throw new IllegalArgumentException("Account Already exists");
        }
        Account saved = repository.save(anAccount);
//        tracker.notifyUserCreation(saved.getId());
//        AccountCreationHandler.AccountCreated event =
//                new AccountCreationHandler.AccountCreated(saved.getName(), saved.getEmail());
//        policies.push(event);
        return saved;

    }

    @Override
    public Account updateMDD(UpdateMDDRequest updateMDDRequest) {
        Account account = finder.findOneById(updateMDDRequest.getId());
        if (exists(account)){
            account.setMdd(account.getMdd() + updateMDDRequest.getAmount());
            if (account.getMdd() < 0) {
                account.setMdd(account.getMdd() - updateMDDRequest.getAmount());
                Account saved = repository.save(account);
                throw new NotEnoughMDDException();
            }
            Account saved = repository.save(account);
            return saved;
        }else {
            throw new UserNotFoundException(updateMDDRequest.getId());
        }
    }


    private boolean exists(Account anAccount) {
        if (!Objects.isNull(anAccount.getId())){
            return repository.findById(anAccount.getId()).isPresent();
        }
        return repository.findByEmail(anAccount.getEmail()).size() > 0;
    }



}
