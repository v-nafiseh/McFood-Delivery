package com.cas735.group2.billingsrv.ports;

import com.cas735.group2.billingsrv.business.entities.Invoice;
import com.cas735.group2.billingsrv.dtos.InvoiceCreationRequest;

public interface InvoiceManagement {

    Invoice create(InvoiceCreationRequest request);

    Invoice findByUserId(Long userId);

}
