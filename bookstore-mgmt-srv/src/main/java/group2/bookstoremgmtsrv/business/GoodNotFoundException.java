package group2.bookstoremgmtsrv.business;

public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException(Long id) {
        super("Could not find Good #" + id);
    }

    public GoodNotFoundException(String title) {
        super("Could not find good with title" + title);
    }
}
