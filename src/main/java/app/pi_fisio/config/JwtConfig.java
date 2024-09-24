package app.pi_fisio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    //Parâmetros para geração do token
    private static String secretKey;
    public static int tokenExpiration;
    public static int tokenRefreshExpiration;

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtConfig.secretKey = secretKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    @Value("${jwt.token.expiration}")
    public void setTokenExpiration(int tokenExpiration) {
        JwtConfig.tokenExpiration = tokenExpiration;
    }

    public static int getTokenExpiration() {
        return tokenExpiration;
    }

    @Value("${jwt.refresh.token.expiration}")
    public void setTokenRefreshExpiration(int tokenRefreshExpiration) {
        JwtConfig.tokenRefreshExpiration = tokenRefreshExpiration;
    }

    public static int getTokenRefreshExpiration() {
        return tokenRefreshExpiration;
    }
}
