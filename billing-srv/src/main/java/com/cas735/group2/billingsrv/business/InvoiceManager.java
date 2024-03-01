package com.cas735.group2.billingsrv.business;

import com.cas735.group2.billingsrv.business.entities.Invoice;
import com.cas735.group2.billingsrv.dtos.InvoiceCreationRequest;
import com.cas735.group2.billingsrv.ports.InvoiceManagement;
import com.cas735.group2.billingsrv.ports.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class InvoiceManager implements InvoiceManagement {

    private final InvoiceRepository repository;

    public InvoiceManager(InvoiceRepository repository) {
        this.repository = repository;
    }


    @Override
    public Invoice create(InvoiceCreationRequest request) {
        Invoice invoice = new Invoice(request.getUserId(), request.getNumberOfProviders(), request.getTotalPrice());
//        if (exists(invoice)){
//            repository.deleteByUserId(request.getUserId());
//        }
        return repository.save(invoice);

    }

    @Override
    public Invoice findByUserId(Long userId) {
        List<Invoice> invoices = repository.findByUserId(userId);
        if (invoices.size() < 1)
            throw new RuntimeException("there is no invoice for this user!");
        return invoices.get(0);
    }

    private boolean exists(Invoice invoice) {
        if (!Objects.isNull(invoice.getId())){
            return repository.findById(invoice.getId()).isPresent();
        }
        return repository.findByUserId(invoice.getUserId()).size() > 0;
    }

}
