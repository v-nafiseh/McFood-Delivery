package group2.foodprovidormgmtsrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class AddProviderRequest implements Serializable {

    private final @NonNull String name;
    private final @NonNull String telephone;


}