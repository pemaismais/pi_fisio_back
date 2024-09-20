package app.pi_fisio.controller;

import app.pi_fisio.dto.LoginDTO;
import app.pi_fisio.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<String> loginWithGoogle(@RequestBody LoginDTO request) {
        try {
            String idTokenString = request.getIdToken();
            return ResponseEntity.ok(loginService.loginWithGoogle(idTokenString));
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}

}