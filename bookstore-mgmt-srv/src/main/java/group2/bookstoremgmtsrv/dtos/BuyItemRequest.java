package group2.bookstoremgmtsrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class BuyItemRequest implements Serializable{

    private final @NonNull Long userId;
    private final @NonNull Long goodId;
    private final @NonNull Double mdd;
    private final @NonNull String address;

}
