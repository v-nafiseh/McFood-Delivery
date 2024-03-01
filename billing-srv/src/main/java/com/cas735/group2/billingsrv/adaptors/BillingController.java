package com.cas735.group2.billingsrv.adaptors;


import com.cas735.group2.billingsrv.business.entities.Invoice;
import com.cas735.group2.billingsrv.dtos.InvoiceCreationRequest;
import com.cas735.group2.billingsrv.ports.InvoiceManagement;
import com.cas735.group2.billingsrv.ports.InvoiceRepository;
import com.cas735.group2.billingsrv.ports.PayManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class BillingController {
    private static final String ENDPOINT = "/pay";
    private final InvoiceRepository repository;
    private final InvoiceManagement management;

    private final PayManagement payManagement;

    @Autowired
    public BillingController(InvoiceRepository repository, InvoiceManagement management, PayManagement payManagement) {
        this.repository = repository;
        this.management = management;
        this.payManagement = payManagement;
    }


    @PostMapping(ENDPOINT+"/create")
    public Invoice create(@RequestBody InvoiceCreationRequest request){
        return management.create(request);
    }

    @GetMapping(ENDPOINT+"/{userId}")
    public void pay(@PathVariable Long userId){
        payManagement.pay(userId);
    }

}
