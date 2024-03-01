package com.cas735.group2.billingsrv.business.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Invoice {
    private @Id
    @GeneratedValue Long id;

    private @NonNull Long userId;
    private @NonNull Integer numberOfProvider;
    private @NonNull Double totalPrice;

}
