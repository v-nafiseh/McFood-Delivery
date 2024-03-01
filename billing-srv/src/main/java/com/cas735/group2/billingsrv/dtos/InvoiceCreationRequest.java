package com.cas735.group2.billingsrv.dtos;


import lombok.NonNull;
import lombok.Value;

@Value
public class InvoiceCreationRequest {
    private final @NonNull Long userId;
    private final @NonNull Integer numberOfProviders;
    private final @NonNull Double totalPrice;
}
