package app.pi_fisio.dto;

import java.util.List;

public record ExercisePageDTO(List<ExerciseDTO> exercises, Long totalElements, int totalPages) {
}
