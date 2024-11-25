package app.pi_fisio.controller;

import app.pi_fisio.auth.RequestAuthDTO;
import app.pi_fisio.auth.RequestRefreshTokenDTO;
import app.pi_fisio.auth.TokenResponseDTO;
import app.pi_fisio.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> authWithGoogle(@RequestBody RequestAuthDTO requestAuthDTO) throws Exception {
        String idTokenString = requestAuthDTO.idToken();
        TokenResponseDTO response = authService.authWithGoogle(idTokenString);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenResponseDTO> authRefreshToken(@RequestBody RequestRefreshTokenDTO refreshTokenDTO) throws Exception {
        String refreshToken = refreshTokenDTO.refreshToken();
        return ResponseEntity.ok(authService.getRefreshToken(refreshToken));
    }
}