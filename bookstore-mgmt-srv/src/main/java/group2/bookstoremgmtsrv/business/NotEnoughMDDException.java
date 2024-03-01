package group2.bookstoremgmtsrv.business;

public class NotEnoughMDDException extends RuntimeException{
    public NotEnoughMDDException(){super("User don't have enough money!");}
}
