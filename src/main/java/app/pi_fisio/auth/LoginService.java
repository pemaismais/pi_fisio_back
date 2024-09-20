//AuthenticationService.java
package app.pi_fisio.auth;

import app.pi_fisio.config.JwtServiceGenerator;
import app.pi_fisio.entity.Person;
import app.pi_fisio.entity.PersonRole;
import app.pi_fisio.repository.PersonRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class LoginService {
    @Value("${google.client-id}")
    private String googleClientId;

    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    PersonRepository personRepository;

    public String loginWithGoogle(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        // Verifica o token ID
        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Get profile information from payload
            String userId = payload.getSubject();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            // Use or store profile information
            Optional<Person> optionalPerson = personRepository.findByEmail(email);
            if (optionalPerson.isPresent()) {
                // Se encontrado no banco de dados
                return jwtService.generateToken(optionalPerson.get());
            } else {
                // Se não encontrado no banco de dados
                Person person = Person.builder()
                        .userId(passwordEncoder.encode(userId))
                        .email(email)
                        .name(name)
                        .role(PersonRole.USER)
                        .build();
                personRepository.save(person);
                return jwtService.generateToken(person);
            }

        } else {
            throw new RuntimeException("Invalid ID token.");
        }
    }

}
