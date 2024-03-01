package group2.bookstoremgmtsrv.business;

import com.sun.istack.FinalArrayList;
import group2.bookstoremgmtsrv.business.entities.Good;
import group2.bookstoremgmtsrv.dtos.AddGoodRequest;
import group2.bookstoremgmtsrv.dtos.BuyItemRequest;
import group2.bookstoremgmtsrv.ports.GoodFinder;
import group2.bookstoremgmtsrv.ports.GoodManagement;
import group2.bookstoremgmtsrv.ports.GoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Service
@Slf4j
public class GoodManager implements GoodManagement {

    private final String USERSERVICEURL = "http://localhost:1000";

    private final GoodRepository repository;
    private final GoodFinder finder;

    public GoodManager(GoodRepository repository, GoodFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    private boolean exists(Good anGood) {
        if (!Objects.isNull(anGood.getId())) {
            return repository.findById(anGood.getId()).isPresent();
        }
        return repository.findByTitle(anGood.getTitle()).size() > 0;
    }

    @Override
    public Good add(AddGoodRequest goodRequest) {
        Good good;
        try {
            good = finder.findOneByTitle(goodRequest.getTitle());
            good.setCount(good.getCount() + 1);

        } catch (GoodNotFoundException e){
            good = new Good(goodRequest.getTitle(), goodRequest.getDescription(), goodRequest.getPrice());
            good.setCount(1);
        }

        Good saved = repository.save(good);
        return saved;
    }

    @Override
    public void buy(BuyItemRequest buyItemRequest) {
        Good good = finder.findOneById(buyItemRequest.getGoodId());

        if (good.getCount() < 1) {
            throw new GoodUnavailableException(buyItemRequest.getGoodId());
        }


        if (buyItemRequest.getMdd() > good.getPrice()){
            good.setCount(good.getCount() - 1);
            try {
                if (sendUpdateRequest(buyItemRequest.getUserId(), good.getPrice())){
                    System.out.printf("Hi");
                    /*  TODO good to be sent to user's address
                        weekly invoice for admin compensation
                    */
                }else {
                    throw new RuntimeException("something went wrong!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            repository.save(good);

        } else {
            throw new NotEnoughMDDException();
        }
    }

    private boolean sendUpdateRequest(long userId, double amount) throws IOException {
        final String postParams = "{ \"id\": " + userId + ", \"amount\": " + -1*amount + "}";
        URL obj = new URL(USERSERVICEURL+"/v1/users/updatemdd");
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(postParams.getBytes());
        os.flush();
        os.close();


        int responseCode = postConnection.getResponseCode();
        return responseCode == 200;
    }
}
