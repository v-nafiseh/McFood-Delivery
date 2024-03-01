package group4.cas735.services.usermgmtsrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class UpdateMDDRequest implements Serializable {
    private final @NonNull Long id;
    private final @NonNull Double amount;

}
