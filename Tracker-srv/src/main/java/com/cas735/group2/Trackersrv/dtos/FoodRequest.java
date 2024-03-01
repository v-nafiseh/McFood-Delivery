package com.cas735.group2.Trackersrv.dtos;

import lombok.NonNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class FoodRequest implements Serializable {

    private final @NonNull String name;
    private final @NonNull Long userId;
    private final @NonNull String providerName;
}
