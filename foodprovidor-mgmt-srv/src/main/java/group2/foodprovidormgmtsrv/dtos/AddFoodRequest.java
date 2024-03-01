package group2.foodprovidormgmtsrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class AddFoodRequest implements Serializable {

    private final @NonNull String name;
    private final @NonNull String description;
    private final @NonNull Double price;
    private final @NonNull String providerName;


}
