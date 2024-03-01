package group2.bookstoremgmtsrv.adaptors;


import group2.bookstoremgmtsrv.business.entities.Good;
import group2.bookstoremgmtsrv.dtos.AddGoodRequest;
import group2.bookstoremgmtsrv.dtos.BuyItemRequest;
import group2.bookstoremgmtsrv.dtos.UserInfoRequest;
import group2.bookstoremgmtsrv.ports.GoodFinder;
import group2.bookstoremgmtsrv.ports.GoodManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class BookStoreController {

    private static final String ENDPOINT = "/bookstore";
    private static final String goodsURL = "/goods";
    private final GoodFinder finder;
    private final GoodManagement goodManagement;

    @Autowired
    public BookStoreController(GoodFinder finder, GoodManagement goodManagement) {
        this.finder = finder;
        this.goodManagement = goodManagement;
    }

    @GetMapping(ENDPOINT+goodsURL)
    public List<Good> findAll() {
        return finder.findAll();
    }

    @GetMapping(ENDPOINT+goodsURL+"/{id}")
    public Good findOneById(@PathVariable Long id) {
        return finder.findOneById(id);
    }

    @GetMapping(ENDPOINT+goodsURL+"/")
    public Good findOneByTitle(@RequestParam String title) {
        return finder.findOneByTitle(title);
    }

    @PostMapping(ENDPOINT+goodsURL+"/add")
    public Good addGood(@RequestBody AddGoodRequest request) {
        return goodManagement.add(request);
    }

    @PostMapping(ENDPOINT+goodsURL+"/catalogue")
    public List<Good> createPersonalizedCatalogue(@RequestBody UserInfoRequest request){
        return finder.catalogue(request.getMdd());
    }

    @PostMapping(ENDPOINT+goodsURL+"/buy")
    public ResponseEntity<Object> buyItem(@RequestBody BuyItemRequest request){
        try {
            goodManagement.buy(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
