package group2.bookstoremgmtsrv.ports;


import group2.bookstoremgmtsrv.business.entities.Good;
import group2.bookstoremgmtsrv.dtos.AddGoodRequest;
import group2.bookstoremgmtsrv.dtos.BuyItemRequest;

public interface GoodManagement {

    Good add(AddGoodRequest goodRequest);

    void buy(BuyItemRequest buyItemRequest);

}
