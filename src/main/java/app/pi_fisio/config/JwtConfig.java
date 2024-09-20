package app.pi_fisio.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    //Parâmetros para geração do token
    public static String secretKey;
    public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256;
    public static final int HORAS_EXPIRACAO_TOKEN = 1;

    @Value("${jwt-client}")
    public void setSecretKey(String secretKey) {
        JwtConfig.secretKey = secretKey;
    }

    public static String getSecretKey() {
        return secretKey; // Método para acessar a variável estática
    }
}
