package app.pi_fisio.service;

//JwtService.java

import app.pi_fisio.config.JwtConfig;
import app.pi_fisio.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    public String generateToken(User userDetails, Integer expiration) throws JWTCreationException {
        return JWT.create()
                .withClaim("username", userDetails.getUsername())
                .withClaim("role", userDetails.getRole().name())
                .withClaim("name", userDetails.getName())
                .withClaim("id", userDetails.getId())
                .withClaim("picture", userDetails.getPictureUrl())
                .withIssuer("PI-Fisio")
                .withSubject(userDetails.getUsername())
                .withExpiresAt(generateExpirationDate(expiration))
                .sign(Algorithm.HMAC256(JwtConfig.getSecretKey()));
    }

    public Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(JwtConfig.getSecretKey()))
                .withIssuer("PI-Fisio")
                .build()
                .verify(token)
                .getSubject();
    }


}
