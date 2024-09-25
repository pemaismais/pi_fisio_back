package app.pi_fisio.controller;


import app.pi_fisio.dto.TokenResponseDTO;
import app.pi_fisio.infra.exception.InvalidGoogleTokenException;
import app.pi_fisio.service.AuthService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @BeforeEach
    void init() throws Exception {
        Mockito.when(authService.authWithGoogle("invalid-id-token")).thenThrow(new InvalidGoogleTokenException("Invalid Google ID Token"));
        Mockito.when(authService.authWithGoogle("id-token")).thenReturn(new TokenResponseDTO("access-token","refresh-token"));
        Mockito.when(authService.getRefreshToken("refresh-token")).thenReturn(new TokenResponseDTO("access-token","refresh-token"));
        Mockito.when(authService.getRefreshToken("invalid-refresh-token")).thenThrow(new JWTDecodeException("Invalid token."));
    }

    @Test
    @DisplayName("When the Google ID Token is invalid.")
    void authWithGoogle01() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idToken\":\"invalid-id-token\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid Google ID Token")); // Update the expected response
    }

    @Test
    @DisplayName("When the Google ID Token is valid.")
    void authWithGoogle02() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idToken\":\"id-token\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    @DisplayName("When the refresh token is valid.")
    void getRefreshToken01() throws Exception{
        mockMvc.perform(post("/auth/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\":\"refresh-token\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    @DisplayName("When the refresh token is invalid.")
    void getRefreshToken02() throws Exception {
        mockMvc.perform(post("/auth/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"refreshToken\":\"invalid-refresh-token\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid token.")); // Update the expected response
    }

}
