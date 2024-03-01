package group4.cas735.services.usermgmtsrv.adapters;

import group4.cas735.services.usermgmtsrv.business.UserManager;
import group4.cas735.services.usermgmtsrv.business.entities.Account;
import group4.cas735.services.usermgmtsrv.dtos.RegistrationRequest;
import group4.cas735.services.usermgmtsrv.dtos.UpdateMDDRequest;
import group4.cas735.services.usermgmtsrv.ports.UserFinder;
import group4.cas735.services.usermgmtsrv.ports.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class UserController {

    private static final String ENDPOINT = "/users";
    private final UserManagement manager;
    private final UserFinder registry;


    @Autowired
    public UserController(UserManager manager, UserFinder registry) {
        this.manager = manager;
        this.registry = registry;
    }

    @GetMapping(ENDPOINT)
    public List<Account> findAll() {
        return registry.findAll();
    }

    @GetMapping(ENDPOINT+"/{id}")
    public Account findOneById(@PathVariable Long id) {
        return registry.findOneById(id);
    }

    @GetMapping(ENDPOINT+"/")
    public Account findOneByEmail(@RequestParam  String email) {
        return registry.findOneByEmail(email);
    }

    @PostMapping(ENDPOINT+"/add")
    public Account create(@RequestBody RegistrationRequest reg) {
        return manager.create(reg);
    }

    @PostMapping(ENDPOINT+"/"+"updatemdd")
    public Account updateMDD(@RequestBody UpdateMDDRequest request){
        return manager.updateMDD(request);
    }


}
