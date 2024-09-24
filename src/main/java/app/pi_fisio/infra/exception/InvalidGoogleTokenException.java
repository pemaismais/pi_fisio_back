package app.pi_fisio.infra.exception;

public class InvalidGoogleTokenException extends RuntimeException{
    public InvalidGoogleTokenException() {
        super("The provided ID token is invalid.");
    }

    public InvalidGoogleTokenException(String message) {
        super(message);
    }
}
