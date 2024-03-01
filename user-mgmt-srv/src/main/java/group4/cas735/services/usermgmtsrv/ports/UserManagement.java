package group4.cas735.services.usermgmtsrv.ports;

import group4.cas735.services.usermgmtsrv.business.entities.Account;
import group4.cas735.services.usermgmtsrv.dtos.RegistrationRequest;
import group4.cas735.services.usermgmtsrv.dtos.UpdateMDDRequest;

public interface UserManagement {

    Account create(RegistrationRequest reg);

    Account updateMDD(UpdateMDDRequest updateMDDRequest);

}
