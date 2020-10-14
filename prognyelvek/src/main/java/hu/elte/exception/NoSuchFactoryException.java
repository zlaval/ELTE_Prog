package hu.elte.exception;

public class NoSuchFactoryException extends RuntimeException {

    public NoSuchFactoryException() {
        super("No factory found on the given type");
    }
}
