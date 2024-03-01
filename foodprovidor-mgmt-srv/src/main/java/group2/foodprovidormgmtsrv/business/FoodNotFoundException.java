package group2.foodprovidormgmtsrv.business;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(String name){super("No food with name " + name + " exists!");}
    public FoodNotFoundException(long id){super("No food with id " + id + " exists!");}
}
