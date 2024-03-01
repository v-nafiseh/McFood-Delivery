package com.cas735.group2.billingsrv.business;

import com.cas735.group2.billingsrv.business.entities.Invoice;
import com.cas735.group2.billingsrv.ports.InvoiceManagement;
import com.cas735.group2.billingsrv.ports.InvoiceRepository;
import com.cas735.group2.billingsrv.ports.PayManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


@Slf4j
@Service
public class PayManager implements PayManagement {

    private final String USER_SERVICE_URL = "http://localhost:1000";
    private final String CART_SERVICE_URL = "http://localhost:4000";

    private final InvoiceRepository repository;
    private final InvoiceManagement management;

    public PayManager(InvoiceRepository repository, InvoiceManagement management) {
        this.repository = repository;
        this.management = management;
    }

    @Override
    public void pay(Long userId) {
        Invoice invoice = management.findByUserId(userId);


        Boolean responseSuccessful = externalBankSystem(true);   // go to payment gateway

        repository.deleteById(invoice.getId());

        if (responseSuccessful) {
            if (invoice.getNumberOfProvider() > 0) {
                try {
                    updateMdd(userId, invoice.getTotalPrice() * CADtoMDD(invoice.getNumberOfProvider()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                updateCartManager(userId);   // update cart manager as a successful payment
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            throw new RuntimeException("Payment was not successful!");
        }
    }

    private Double CADtoMDD(int numberOfProviders) {
        if (numberOfProviders < 1)
            return 0.0;
        else if (numberOfProviders >= 4)
            numberOfProviders = 4;
        return 0.05 * (numberOfProviders - 1);
    }

    private void updateMdd(Long userId, Double amountInMdd) throws IOException {
        final String postParams = "{ \"id\": " + userId + ", \"amount\": " + amountInMdd + "}";
        URL obj = new URL(USER_SERVICE_URL + "/v1/users/updatemdd");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(postParams.getBytes());
        os.flush();
        os.close();


        int responseCode = postConnection.getResponseCode();
    }

    private void updateCartManager(Long userId) throws IOException {
        URL obj = new URL(CART_SERVICE_URL + "/v1/shoppingCart/paymentDone/" + userId.toString());
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("GET");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.getResponseCode();
    }

    @Override
    public boolean externalBankSystem(Boolean successful) {
        return successful;
    }
}
