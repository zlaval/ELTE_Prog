package preexam.delivery.exceptions;

public class WrongZipCodeException extends RuntimeException {

    private int zip;

    public WrongZipCodeException(int zip) {
        this.zip=zip;
    }

}
