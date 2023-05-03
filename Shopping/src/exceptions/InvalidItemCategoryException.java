package exceptions;

public class InvalidItemCategoryException extends Exception {

    public InvalidItemCategoryException() {
    }

    public InvalidItemCategoryException(String message) {
        super(message);
    }

}
