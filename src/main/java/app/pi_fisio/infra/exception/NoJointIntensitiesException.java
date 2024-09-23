package app.pi_fisio.infra.exception;

public class NoJointIntensitiesException  extends RuntimeException{
    public NoJointIntensitiesException() {
    }

    public NoJointIntensitiesException(String message) {
        super(message);
    }
}
