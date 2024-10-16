package app.pi_fisio.dto;

import app.pi_fisio.entity.Intensity;
import app.pi_fisio.entity.Joint;

import java.util.Set;

public record ExerciseFilterDTO(Set<Joint> joints, Set<Intensity> Intensities, String name) {
}
