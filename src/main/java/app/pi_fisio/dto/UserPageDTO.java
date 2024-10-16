package app.pi_fisio.dto;

import app.pi_fisio.entity.User;

import java.util.List;

public record UserPageDTO(List<UserDTO> users, Long totalElements, int totalPages) {
}
