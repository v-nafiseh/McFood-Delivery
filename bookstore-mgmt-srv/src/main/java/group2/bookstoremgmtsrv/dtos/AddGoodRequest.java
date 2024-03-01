package group2.bookstoremgmtsrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class AddGoodRequest implements Serializable {

    private final @NonNull String title;
    private final @NonNull String description;
    private final @NonNull Double price;


}
