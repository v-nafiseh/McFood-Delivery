package com.cas735.group2.billingsrv.ports;

public interface PayManagement {

    public void pay(Long userId);

    public boolean externalBankSystem(Boolean successful);
}
