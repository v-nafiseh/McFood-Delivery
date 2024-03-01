package group4.cas735.services.usermgmtsrv.business;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("Could not find user #" + id);
    }

    public UserNotFoundException(String id) {
        super("Could not find user with email" + id);
    }

}

