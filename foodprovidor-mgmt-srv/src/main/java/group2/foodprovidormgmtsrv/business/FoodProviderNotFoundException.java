package group2.foodprovidormgmtsrv.business;

public class FoodProviderNotFoundException extends RuntimeException{

    public FoodProviderNotFoundException(String name){super("No food provider with name " + name + " exists!");}
    public FoodProviderNotFoundException(long id){super("No food provider with id " + id + " exists!");}

}
