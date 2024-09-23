package app.pi_fisio.infra.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String identifierType, String identifier) {
        super("User with " + identifierType + ": " + identifier + " could not be found.");
    }
    public UserNotFoundException( ) {
        super("User could not be found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
