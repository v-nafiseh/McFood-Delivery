package group2.foodprovidormgmtsrv.business.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class FoodProvider {
    private @Id @GeneratedValue Long id;
    private @NonNull String name;
    private @NonNull String telephone;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodProvider foodProvider = (FoodProvider) o;
        return Objects.equals(id, foodProvider.id) && name.equals(foodProvider.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
