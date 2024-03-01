package group4.cas735.services.usermgmtsrv.business;

public class NotEnoughMDDException extends RuntimeException{
    public NotEnoughMDDException(){super("User don't have enough mdd!");}
}
