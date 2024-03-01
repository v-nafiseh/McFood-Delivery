package com.example.mcfood.service.impl;

import com.example.mcfood.model.Food;
import com.example.mcfood.repository.FoodRepository;
import com.example.mcfood.service.CartManagement;
import com.example.mcfood.service.FoodManagement;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static org.springframework.http.HttpHeaders.USER_AGENT;


@Slf4j
@Service
public class CartManager implements CartManagement {

    private static final String BILLING_SERVICE_URL = "http://localhost:3000";
    private static final String TRACKER_SERVICE_URL = "http://localhost:6111";
    private final FoodManagement foodManagement;
    private final FoodRepository repository;

    @Autowired
    public CartManager(FoodManagement foodManagement, FoodRepository repository) {
        this.foodManagement = foodManagement;
        this.repository = repository;
    }


    @Override
    public void addFood(Long userId, Long productId) {
        Food food = null;
        try {
            food = sendProductRequest(userId, productId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        repository.save(food);

    }


    @Override
    public List<Food> cartAsaList(Long userId){
        List<Food> foods = repository.findAll();
        ArrayList<Food> cart = new ArrayList<>();
        for (Food f: foods){
            if (f.getUserId() == userId){
                cart.add(f);
            }
        }
        return cart;
    }

    private Food sendProductRequest(Long userId, Long productId) throws IOException {
        URL urlForGetRequest = new URL("http://localhost:2000/v1/foods/" + productId);
        HttpURLConnection con = (HttpURLConnection) urlForGetRequest.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

        JSONParser parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(response.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject provider;

        try {
            provider = (JSONObject) parser.parse(json.get("foodProvider").toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(json.get("name"));
        Food food = new Food(userId, json.get("name").toString(), json.get("description").toString(),
                Double.valueOf(json.get("price").toString()), provider.get("name").toString());
        return food;
    }

    @Override
    public void removeFood(Long foodId) {
        repository.deleteById(foodId);
    }

    @Override
    public Map<Food, Integer> getProductsInCart(Long userId) {
        Map<Food, Integer> products = foodManagement.getCart(userId);
        return Collections.unmodifiableMap(products);
    }

    @Override
    public void checkout(Long userId) throws IOException {
        if (!createAndSendInvoice(userId))
            throw new RuntimeException("something went wrong!");
    }

    @Override
    public void paymentFinished(Long userId) {
        System.out.println("done" + userId);
        //TODO add what is inside cart as a pending food to tracking service
        List<Food> cart = cartAsaList(userId);
        updateTracker(cart);
        emptyCart(userId);

    }

    public void updateTracker(List<Food> cart){
        String postParams = "[";
        for (Food f: cart){
            postParams += "{ \"name\": \"" + f.getName() + "\"";
            postParams += ", \"userId\": " + f.getUserId() ;
            postParams += ", \"providerName\": \"" + f.getFoodProvider() + "\"},";
        }
        postParams = postParams.substring(0, postParams.length()-1);
        postParams += "]";
        try {
            sendUpdateTracker(postParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sendUpdateTracker(String params) throws IOException {
        URL obj = new URL(TRACKER_SERVICE_URL + "/v1/tracker/add");

        System.out.println(params);
        System.out.println(obj.toString());
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();


        int responseCode = postConnection.getResponseCode();
        System.out.println(responseCode);
        return responseCode == 200;
    }

    private Integer findNumberOfProviders(Long userId) {
        List<Food> foods = repository.findAll();
        Set<String> providers = new HashSet<>();
        for (Food f : foods) {
            if (f.getUserId() == userId) {
                providers.add(f.getFoodProvider());
            }
        }
        return providers.size();
    }

    private Boolean createAndSendInvoice(Long userId) throws IOException {
        final String postParams = "{ \"userId\": " + userId + ", \"numberOfProviders\": "
                + findNumberOfProviders(userId) + ", \"totalPrice\": " +  + calculateTotalPrice(userId) + "}" ;
        URL obj = new URL(BILLING_SERVICE_URL + "/v1/pay/create");

//        System.out.println(postParams);
//        System.out.println(obj.toString());
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


    @Override
    public Double calculateTotalPrice(Long userId) {
        Map<Food, Integer> products = foodManagement.getCart(userId);
        double total = 0;
        for (Food p : products.keySet()) {
            total += products.get(p) * p.getPrice();
        }
        return total;
    }

    @Override
    public void emptyCart(Long userId) {
        List<Food> foods = repository.findAll();
        for (Food f : foods) {
            if (f.getUserId() == userId) {
                repository.deleteById(f.getId());
            }
        }
    }


}
