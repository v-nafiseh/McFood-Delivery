package com.cas735.group2.billingsrv.ports;

import com.cas735.group2.billingsrv.business.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByUserId(Long userId);

}
