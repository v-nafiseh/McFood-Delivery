package group4.cas735.services.usermgmtsrv.dtos;


import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class RegistrationRequest implements Serializable {

    private final @NonNull String name;
    private final @NonNull String email;


}
