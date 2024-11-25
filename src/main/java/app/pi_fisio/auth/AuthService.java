//AuthenticationService.java
package app.pi_fisio.auth;

import app.pi_fisio.config.JwtConfig;
import app.pi_fisio.entity.User;
import app.pi_fisio.entity.UserRole;
import app.pi_fisio.infra.exception.InvalidGoogleTokenException;
import app.pi_fisio.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {
    @Value("${google.client.id}")
    private String googleClientId;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public TokenResponseDTO authWithGoogle(String idTokenString) throws Exception {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        // Verifica o token ID
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new InvalidGoogleTokenException("Invalid Google ID Token");
        }
        // Get profile information from payload
        GoogleIdToken.Payload payload = idToken.getPayload();
        String userId = payload.getSubject();
        String email = payload.getEmail();
        String picture = (String) payload.get("picture");
        String name = (String) payload.get("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createUser(userId, email, name, picture));

        return createTokenResponse(user);
    }

    public TokenResponseDTO getRefreshToken(String refreshToken) throws Exception {
        String userLogin = jwtService.validateToken(refreshToken);
        Optional<User> optionalUser = userRepository.findByEmail(userLogin);

        var authentication = new UsernamePasswordAuthenticationToken(optionalUser.get(), null, optionalUser.get().getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createTokenResponse(optionalUser.get());

    }

    private User createUser(String userId, String email, String name, String picture) {
        User user = User.builder()
                .userId(passwordEncoder.encode(userId))
                .email(email)
                .name(name)
                .role(UserRole.USER)
                .pictureUrl(picture)
                .build();
        userRepository.save(user);
        return user;
    }

    private TokenResponseDTO createTokenResponse(User user) {
        return TokenResponseDTO.builder()
                .accessToken(jwtService.generateToken(user, JwtConfig.getTokenExpiration()))
                .refreshToken(jwtService.generateToken(user, JwtConfig.getTokenRefreshExpiration()))
                .build();
    }
}
