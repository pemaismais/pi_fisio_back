package app.pi_fisio.controller;

import app.pi_fisio.dto.RequestAuthDTO;
import app.pi_fisio.dto.RequestRefreshTokenDTO;
import app.pi_fisio.dto.TokenResponseDTO;
import app.pi_fisio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> authWithGoogle(@RequestBody RequestAuthDTO requestAuthDTO) {
        try {
            String idTokenString = requestAuthDTO.idToken();
            return ResponseEntity.ok(authService.authWithGoogle(idTokenString));

        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenResponseDTO> authRefreshToken(@RequestBody RequestRefreshTokenDTO refreshTokenDTO) {
        try {
            String refreshToken = refreshTokenDTO.refreshToken();
            System.out.println(refreshToken);
            return ResponseEntity.ok(authService.getRefreshToken(refreshToken));

        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}