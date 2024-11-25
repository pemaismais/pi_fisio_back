package app.pi_fisio.auth;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String accessToken, String refreshToken) {
}
