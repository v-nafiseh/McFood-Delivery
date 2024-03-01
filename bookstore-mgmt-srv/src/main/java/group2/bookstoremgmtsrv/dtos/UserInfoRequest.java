package group2.bookstoremgmtsrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class UserInfoRequest implements Serializable {

    private final @NonNull Long userId;
    private final @NonNull Double mdd;
}
