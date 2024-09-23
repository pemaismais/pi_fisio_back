package app.pi_fisio.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
public class DefaultErrorMessage {
    private int status;
    private String error;
    private String message;
}
