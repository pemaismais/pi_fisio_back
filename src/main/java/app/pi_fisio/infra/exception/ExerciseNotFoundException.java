package app.pi_fisio.infra.exception;

public class ExerciseNotFoundException extends RuntimeException{
    public ExerciseNotFoundException() {
        super("Exercise could not be found.");
    }

    public ExerciseNotFoundException(String message) {
        super(message);
    }
}
