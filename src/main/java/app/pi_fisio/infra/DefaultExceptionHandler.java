package app.pi_fisio.infra;

import app.pi_fisio.infra.exception.ExerciseNotFoundException;
import app.pi_fisio.infra.exception.InvalidGoogleTokenException;
import app.pi_fisio.infra.exception.NoJointIntensitiesException;
import app.pi_fisio.infra.exception.UserNotFoundException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), "User not found.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(defaultErrorMessage);
    }

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> exerciseNotFoundHandler(ExerciseNotFoundException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), "Exercise not found.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(defaultErrorMessage);
    }

    @ExceptionHandler(NoJointIntensitiesException.class)
    public ResponseEntity<DefaultErrorMessage> noJointIntensitiesHandler(NoJointIntensitiesException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), "Exercises not found.",  exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(defaultErrorMessage);
    }

    @ExceptionHandler(InvalidGoogleTokenException.class)
    public ResponseEntity<DefaultErrorMessage> invalidGoogleTokenHandler(InvalidGoogleTokenException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Invalid token.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(defaultErrorMessage);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<DefaultErrorMessage> jwtDecodeHandler(JWTDecodeException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Invalid token.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(defaultErrorMessage);
    }

    @ExceptionHandler({JWTCreationException.class})
    public ResponseEntity<DefaultErrorMessage> jwtCreationHandler(JWTCreationException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Token could not be created.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(defaultErrorMessage);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<DefaultErrorMessage> tokenExpiredHandler(TokenExpiredException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Token expired.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(defaultErrorMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DefaultErrorMessage> authenticationHandler(AuthenticationException exception) {
        DefaultErrorMessage defaultErrorMessage = new DefaultErrorMessage(HttpStatus.UNAUTHORIZED.value(), "The provided credentials are incorrect.", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(defaultErrorMessage);
    }

}
