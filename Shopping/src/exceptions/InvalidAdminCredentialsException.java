package exceptions;

public class InvalidAdminCredentialsException extends Exception {

    public InvalidAdminCredentialsException() {
    }

    public InvalidAdminCredentialsException(String message) {
        super(message);
    }
}
