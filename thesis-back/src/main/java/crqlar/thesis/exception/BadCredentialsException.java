package crqlar.thesis.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super("Bad credentials!");
    }
}
