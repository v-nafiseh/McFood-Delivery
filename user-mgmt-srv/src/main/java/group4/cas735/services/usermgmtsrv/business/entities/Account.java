package group4.cas735.services.usermgmtsrv.business.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter
@ToString
public class Account  {

    private @Id @GeneratedValue Long id;
    private @NonNull String name;
    private @NonNull String email;

    private @NonNull Double mdd = Double.valueOf(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && name.equals(account.name) && email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
