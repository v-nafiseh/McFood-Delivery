package group2.bookstoremgmtsrv.business.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Good {
    private @Id @GeneratedValue Long id;
    private @NonNull String title;
    private @NonNull String description;
    private @NonNull int count=0;
    private @NonNull double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return Objects.equals(id, good.id) && title.equals(good.title) &&
                description.equals(good.description) && price == good.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }
}
