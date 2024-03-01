package group2.bookstoremgmtsrv.business;

public class GoodUnavailableException extends RuntimeException {
    public GoodUnavailableException(Long id) {
        super("There is no good with id " + id + " available.");
    }
}
