package app.pi_fisio.service;

import app.pi_fisio.dto.TokenResponseDTO;
import app.pi_fisio.entity.User;
import app.pi_fisio.entity.UserRole;
import app.pi_fisio.infra.exception.InvalidGoogleTokenException;
import app.pi_fisio.repository.UserRepository;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.webtoken.JsonWebSignature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtService jwtService;

    @BeforeEach
    void setup() throws Exception {
        User user = User.builder().email("pedro@email.com").name("pedro").userId("pedro11023").role(UserRole.USER).build();
        when(userRepository.findByEmail("pedro@email.com")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("novopedro@email.com")).thenReturn(Optional.empty());
        when(jwtService.generateToken(Mockito.any(), Mockito.any())).thenCallRealMethod();
        when(jwtService.generateExpirationDate(Mockito.any())).thenCallRealMethod();
        when(jwtService.validateToken("refresh-token")).thenReturn("pedro@email.com");
        when(jwtService.validateToken("invalid-refresh-token")).thenCallRealMethod();
    }

    private GoogleIdToken createMockGoogleIdToken(String email, String subject, String name) {
        GoogleIdToken.Payload mockPayload = new GoogleIdToken.Payload()
                .setEmail(email)
                .setSubject(subject)
                .set("name", name);

        return new GoogleIdToken(
                new JsonWebSignature.Header(),
                mockPayload,
                new byte[1],
                new byte[1]
        );
    }

    // Referencia do mock: https://stackoverflow.com/a/72595913
    @Test
    @DisplayName("Auth with valid Google token and in the DB.")
    void authWithGoogle01() throws Exception {
        GoogleIdToken mockGoogleIdToken = createMockGoogleIdToken("pedro@email.com", "pedro11023", "pedro");

        try (MockedConstruction<GoogleIdTokenVerifier> myobjectMockedConstruction =
                     Mockito.mockConstruction(GoogleIdTokenVerifier.class,
                             (mock, context) -> {
                                 given(mock.verify("token-google")).willReturn(mockGoogleIdToken);
                             })) {

            TokenResponseDTO response = authService.authWithGoogle("token-google");

            Assertions.assertNotNull(response.accessToken(), response.refreshToken());
            verify(userRepository).findByEmail("pedro@email.com");
        }
    }

    @Test
    @DisplayName("Auth with valid Google token but not in the DB.")
    void authWithGoogle02() throws Exception {
        GoogleIdToken mockGoogleIdToken = createMockGoogleIdToken("novopedro@email.com", "pedro1000", "pedro henrique");

        try (MockedConstruction<GoogleIdTokenVerifier> myobjectMockedConstruction =
                     Mockito.mockConstruction(GoogleIdTokenVerifier.class,
                             (mock, context) -> {
                                 given(mock.verify("token-google")).willReturn(mockGoogleIdToken);
                             })) {


            TokenResponseDTO response = authService.authWithGoogle("token-google");

            Assertions.assertNotNull(response.accessToken(), response.refreshToken());
            verify(userRepository).findByEmail("novopedro@email.com");
        }
    }

    @Test
    @DisplayName("Auth with invalid Google token.")
    void authWithGoogle03() {
        try (MockedConstruction<GoogleIdTokenVerifier> myobjectMockedConstruction =
                     Mockito.mockConstruction(GoogleIdTokenVerifier.class,
                             (mock, context) -> {
                                 given(mock.verify("token-google")).willReturn(null);
                             })) {

            Assertions.assertThrows(InvalidGoogleTokenException.class,
                    () -> authService.authWithGoogle("token-google"));
        }
    }

    @Test
    @DisplayName("Refresh token with a valid token.")
    void authRefreshToken01() throws Exception {
        TokenResponseDTO response = authService.getRefreshToken("refresh-token");

        Assertions.assertNotNull(response.accessToken(), response.refreshToken());
    }
    @Test
    @DisplayName("Refresh token with a invalid token.")
    void authRefreshToken02() throws Exception {
        Assertions.assertThrows(JWTVerificationException.class,
                () -> authService.getRefreshToken("invalid-refresh-token"));
    }

}
