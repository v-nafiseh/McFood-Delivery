package com.cas735.group2.Trackersrv.business.entities;


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
public class Food {
    private @Id
    @GeneratedValue Long id;

    private @NonNull Long userId;
    private @NonNull String name;

    private @NonNull String foodProvider;

    private @NonNull Status status;

}
