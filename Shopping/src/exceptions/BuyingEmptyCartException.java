package exceptions;

public class BuyingEmptyCartException extends Exception {

    public BuyingEmptyCartException() {
    }

    public BuyingEmptyCartException(String message) {
        super(message);
    }

}
