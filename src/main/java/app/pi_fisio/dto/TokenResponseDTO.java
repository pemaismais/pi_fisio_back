package app.pi_fisio.dto;

import lombok.Builder;

@Builder
public record TokenResponseDTO(String accessToken, String refreshToken) {
}
